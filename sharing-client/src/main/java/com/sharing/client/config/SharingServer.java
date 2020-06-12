package com.sharing.client.config;

import com.netflix.loadbalancer.Server;

public class SharingServer extends Server {

    public SharingServer(String host, int port) {
        super(host, port);
    }
}
