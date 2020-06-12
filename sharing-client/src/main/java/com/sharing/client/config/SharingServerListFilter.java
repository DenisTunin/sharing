package com.sharing.client.config;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;

import java.util.List;
import java.util.stream.Collectors;

public class SharingServerListFilter<T extends Server> implements ServerListFilter <T>{
    @Override
    public List<T> getFilteredListOfServers(List<T> servers) {
        return servers.stream().filter(Server::isAlive).collect(Collectors.toList());
    }
}
