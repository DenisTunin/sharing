package com.sharing.client.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

import java.util.ArrayList;
import java.util.List;

public class SharingServerList implements ServerList<Server> {

    private final EurekaClient eurekaClient;
    private final String name;

    public SharingServerList(EurekaClient eurekaClient, String name) {
        this.eurekaClient = eurekaClient;
        this.name = name;
    }

    @Override
    public List<Server> getInitialListOfServers() {
        return obtainServersViaDiscovery();
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        return obtainServersViaDiscovery();
    }

    private List<Server> obtainServersViaDiscovery() {
        List<Server> serverList = new ArrayList<>();

        List<InstanceInfo> listOfInstanceInfo = eurekaClient.getApplication(name).getInstances();
        for (InstanceInfo ii : listOfInstanceInfo) {
            if (ii.getStatus().equals(InstanceInfo.InstanceStatus.UP)) {
                SharingServer des = new SharingServer(ii.getHostName(), ii.getPort());
                serverList.add(des);
            }
        }

        return serverList;
    }
}
