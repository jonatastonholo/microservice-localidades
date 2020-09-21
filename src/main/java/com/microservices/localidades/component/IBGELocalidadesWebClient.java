package com.microservices.localidades.component;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class IBGELocalidadesWebClient {
    private final String baseURL = "https://servicodados.ibge.gov.br/api/v1/localidades";

    @Bean
    public WebClient _IBGELocalidadesWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(baseURL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
