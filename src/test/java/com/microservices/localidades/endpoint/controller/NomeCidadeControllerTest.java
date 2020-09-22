package com.microservices.localidades.endpoint.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Jônatas Tonholo
 * The unit test for NomeCidadeController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NomeCidadeControllerTest {

    @Autowired
    NomeCidadeController nomeCidadeController;

    @Test
    void getNomeCidadeWithoutCache() {
        try {
            String resultStr = nomeCidadeController.getNomeCidadeWithoutCache("Betim").getStatusCode().toString();
            assert resultStr.contains(HttpStatus.OK.toString());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void getNomeCidadeWithCache() {
        try {
            List<String> cidades = new ArrayList<String>();
            cidades.add("NoVa VicoSa");
            cidades.add("Nova Viçosa");
            cidades.add("Betim");
            cidades.add("Bogotá");
            for(String cidade : cidades) {
                String resultStr = nomeCidadeController.getNomeCidadeWithCache(cidade).getStatusCode().toString();
                if (!(resultStr.contains(HttpStatus.OK.toString()) || resultStr.contains(HttpStatus.NOT_FOUND.toString()))) assert false;
            }
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
}