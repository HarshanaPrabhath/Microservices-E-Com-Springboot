package com.ecommerce.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/build")
@ConfigurationProperties(prefix = "build")
public class ConfigCheck {

    private String name;
    private String configuration;
    private String version;

    @GetMapping
    public String checkConfig() {
        return "Name: " + name + " | Config: " + configuration + " | Version: " + version;
    }
}