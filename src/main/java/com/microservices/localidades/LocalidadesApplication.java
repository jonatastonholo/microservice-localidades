package com.microservices.localidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Jônatas Tonholo
 * A microservice to consume the Rest API of states and municipalities provided by IBGE
 */
@SpringBootApplication
@EnableCaching
public class LocalidadesApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalidadesApplication.class, args);
    }

}
