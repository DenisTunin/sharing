package com.sharing.client.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

public class SharingRule implements IRule {

    private static final Random RANDOM = new Random();

    private ILoadBalancer lb;

    @Override
    public Server choose(Object key) {
        if (lb == null) {
            return null;
        }
       Server server;

        while (true) {

            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }

            server = upList.get(RANDOM.nextInt(upList.size()));

            if (server.isAlive()) {
                return server;
            }
        }
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return lb;
    }
}
