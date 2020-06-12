package com.sharing.client.config;

import com.netflix.loadbalancer.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SharingLoadBalancer implements ILoadBalancer {

    private final Pinger pinger = new Pinger(new SharingPingStrategy());

    private final SharingServerListUpdater sharingServerListUpdater = new SharingServerListUpdater();

    private final SharingServerListFilter<Server>  filter = new SharingServerListFilter<>();

    private List<Server> allServerList = List.of();
    private List<Server> upServerList = List.of();

    private IPing ping = new SharingPing();
    private IRule rule;

    public SharingLoadBalancer(IRule rule, SharingServerList sharingServerList){
        this.rule = rule;
        rule.setLoadBalancer(this);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(this::ping, 0, 1000, TimeUnit.MILLISECONDS);
        sharingServerListUpdater.start(() -> addServers(sharingServerList.getInitialListOfServers()));
    }

    @Override
    public synchronized void addServers(List<Server> newServers) {
        if (newServers != null && newServers.size() > 0) {
            allServerList = new ArrayList<>(filter.getFilteredListOfServers(newServers));
        }
    }

    @Override
    public Server chooseServer(Object key) {
        return rule.choose(key);
    }

    @Override
    public void markServerDown(Server server) {
        if (server == null || !server.isAlive()) {
            return;
        }
        server.setAlive(false);
    }

    @Override
    public List<Server> getServerList(boolean availableOnly) {
        return (availableOnly ? getReachableServers() : getAllServers());
    }

    @Override
    public List<Server> getReachableServers() {
        return Collections.unmodifiableList(upServerList);
    }

    @Override
    public List<Server> getAllServers() {
        return Collections.unmodifiableList(allServerList);
    }

    public synchronized void ping(){
        pinger.runPinger();
    }

    class Pinger {

        private final IPingStrategy pingerStrategy;

        public Pinger(IPingStrategy pingerStrategy) {
            this.pingerStrategy = pingerStrategy;
        }

        public void runPinger() {

            Server[] allServers = allServerList.toArray(new Server[allServerList.size()]);
            boolean[] results = pingerStrategy.pingServers(ping, allServers);
            final List<Server> newUpList = new ArrayList<Server>();

            for (int i = 0; i < allServers.length; i++) {
                boolean isAlive = results[i];
                Server svr = allServers[i];
                svr.setAlive(isAlive);
                if (isAlive) {
                    newUpList.add(svr);
                }
            }
            upServerList = newUpList;
        }
    }
}
