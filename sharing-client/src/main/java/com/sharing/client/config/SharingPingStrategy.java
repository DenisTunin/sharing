package com.sharing.client.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IPingStrategy;
import com.netflix.loadbalancer.Server;

public class SharingPingStrategy implements IPingStrategy {
    @Override
    public boolean[] pingServers(IPing ping, Server[] servers) {
        boolean[] b = new boolean[servers.length];
        for(int i = 0; i < servers.length; i++){
            b[i] = ping.isAlive(servers[i]);
        }
        return b;
    }
}
