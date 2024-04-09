package com.example.zzyclientsdk;

import com.example.zzyclientsdk.client.ZzyApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "zzy.client")
@Data
@ComponentScan
public class zzyApiClientConfig {
    private String accessKey;
    private String secretKey;
    @Bean
    public ZzyApiClient zzyApiClient(){
        return  new ZzyApiClient(accessKey, secretKey);
    }
}
