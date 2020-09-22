package com.microservices.localidades.component;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Jônatas Tonholo
 * A microservice to consume the Rest API of states and municipalities provided by IBGE
 */
@Component
public class IBGELocalidadesHTTPClient {
    private static final String BASE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades";

    @Bean
    public WebClient webClientIBGELocalidades(WebClient.Builder builder) {
        return builder
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
