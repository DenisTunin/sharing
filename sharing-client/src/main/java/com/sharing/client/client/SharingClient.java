package com.sharing.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "sharing-service")
public interface SharingClient {

    @GetMapping("/service")
    String mock();
}
