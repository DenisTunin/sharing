package com.sharing.service.controller;

import com.netflix.appinfo.EurekaInstanceConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SharingController {

    private final EurekaInstanceConfig instanceConfig;

    public SharingController(EurekaInstanceConfig instanceConfig){
        this.instanceConfig = instanceConfig;
    }

    @GetMapping("/service")
    public String mock(){
        return instanceConfig.getInstanceId();
    }
}
