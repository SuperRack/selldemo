package com.example.selldemo2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "github")
public class GithubConfig {

    private String ClientId;

    private String ClinetSecret;

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getClinetSecret() {
        return ClinetSecret;
    }

    public void setClinetSecret(String clinetSecret) {
        ClinetSecret = clinetSecret;
    }
}
