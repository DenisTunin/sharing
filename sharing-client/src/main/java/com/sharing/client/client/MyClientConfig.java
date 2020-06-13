package com.sharing.client.client;

import com.netflix.client.config.DefaultClientConfigImpl;

public class MyClientConfig extends DefaultClientConfigImpl {

    public String getNameSpace() {
        return "sharing";
    }

}
