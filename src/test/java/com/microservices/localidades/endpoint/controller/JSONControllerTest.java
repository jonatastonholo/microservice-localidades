package com.microservices.localidades.endpoint.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
/**
 * @author Jônatas Tonholo
 * The unit test for JSONController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JSONControllerTest {

    @Autowired
    JSONController jsonController;

    @Test
    void getJSONLocalidadesWithoutCache() {
        try {
            String resultStr = jsonController.getLocalidadesWithoutCache().getStatusCode().toString();
            assert resultStr.contains(HttpStatus.OK.toString());
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    void getLocalidadesWithCache() {
        try {
            String resultStr = jsonController.getLocalidadesWithCache().getStatusCode().toString();
            assert resultStr.contains(HttpStatus.OK.toString());
        } catch (Exception e) {
            assert false;
        }
    }
}