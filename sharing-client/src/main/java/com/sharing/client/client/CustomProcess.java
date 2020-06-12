//package com.sharing.client.client;
//
//import com.netflix.client.ClientException;
//import com.netflix.client.config.DefaultClientConfigImpl;
//import com.netflix.discovery.EurekaClient;
//import com.netflix.loadbalancer.IRule;
//import com.sharing.client.config.SharingLoadBalancer;
//import com.sharing.client.config.SharingRule;
//import com.sharing.client.config.SharingServerList;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpRequest;
//import org.springframework.cloud.netflix.ribbon.apache.RibbonLoadBalancingHttpClient;
//import org.springframework.cloud.netflix.ribbon.eureka.EurekaServerIntrospector;
//import org.springframework.cloud.netflix.ribbon.support.RibbonCommandContext;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Component
//public class CustomProcess {
//
//    private final Logger logger = LoggerFactory.getLogger(CustomProcess.class);
//
//
//    private final RibbonLoadBalancingHttpClient client;
//    private final SharingLoadBalancer sharingLoadBalancer;
//    private final SharingServerList sharingServerList;
//
//    public CustomProcess(EurekaClient eurekaClient){
//        sharingServerList = new SharingServerList(eurekaClient, "sharing-service");
//        client = new RibbonLoadBalancingHttpClient(
//                new DefaultClientConfigImpl(), new EurekaServerIntrospector());
//        IRule rule = new SharingRule();
//        sharingLoadBalancer = new SharingLoadBalancer(rule, sharingServerList);
//        client.setLoadBalancer(sharingLoadBalancer);
//    }
//
//
//    @Scheduled(fixedDelay = 1000)
//    public void process() throws ClientException, IOException {
//        RibbonApacheHttpRequest httpRequest = new RibbonApacheHttpRequest(
//                new RibbonCommandContext("sharing-service", "GET", "/service",
//                        false, new LinkedMultiValueMap<>(), new LinkedMultiValueMap<>(), null,
//                        new ArrayList<>()));
//
//        logger.info("response '{}'",
//                new String(client.executeWithLoadBalancer(httpRequest).getInputStream().readAllBytes()));
//    }
//}
