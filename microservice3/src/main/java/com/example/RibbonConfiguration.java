package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.WeightedResponseTimeRule;

public class RibbonConfiguration {
	
    @Autowired
    IClientConfig ribbonClientConfig;

    /**
     * Determine the server’s availability in real-time
     * @param config
     * @return
     */
    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    /**
     * According to this rule, each server is given a weight according to its average response time, 
     * lesser the response time gives lesser the weight. 
     * This rule randomly selects a server where the possibility is determined by server’s weight.
     * @param config
     * @return
     */
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new WeightedResponseTimeRule();
    }

}
