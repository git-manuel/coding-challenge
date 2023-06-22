package com.coding.challenge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "docker")
@Data
public class DockerConfig {
    
    private String vmURL;
    private String containerURL;

}