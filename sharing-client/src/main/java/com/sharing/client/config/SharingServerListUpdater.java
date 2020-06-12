package com.sharing.client.config;

import com.netflix.loadbalancer.ServerListUpdater;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SharingServerListUpdater implements ServerListUpdater {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void start(UpdateAction updateAction) {
        executor.scheduleWithFixedDelay(updateAction::doUpdate, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    @Override
    public String getLastUpdate() {
        return null;
    }

    @Override
    public long getDurationSinceLastUpdateMs() {
        return 0;
    }

    @Override
    public int getNumberMissedCycles() {
        return 0;
    }

    @Override
    public int getCoreThreads() {
        return 0;
    }
}
