package com.sharing.client.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SharingPing implements IPing {
    private static final int TIMEOUT = 1000;

    @Override
    public boolean isAlive(Server server) {
        return pingURL("http://" + server.getHostPort());
    }

    private static boolean pingURL(String url) {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode < 500;
        } catch (IOException exception) {
            return false;
        }
    }
}
