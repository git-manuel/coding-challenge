package com.coding.challenge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ks8")
@Data
public class KubernetesConfig {
    
    private String serviceURL;
    private String clusterURL;

}