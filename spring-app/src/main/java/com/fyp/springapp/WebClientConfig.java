package com.fyp.springapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class WebClientConfig {
    private static final String baseApiUrl = "https://api.nationaltransport.ie";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseApiUrl)
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(1024 * 1024) // 1 MB
                )
                .build();
    }
}
