package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jônatas Tonholo
 *
 * Microservice Endpoint for get JSON from IBGE's REST API of "Localidades" (Locations)
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JSONController {
    @Autowired
    private IBGEApiService ibgeApiService;

    /**
     * Request to IBGE's API the Localidades without caching and return a JSON as response
     * @return ResponseEntity
     */
    @GetMapping(value = "localidades/json")
    public ResponseEntity getLocalidadesWithoutCache() {
        log.debug("JSONController.getLocalidadesWithoutCache");
        log.info("localidades/json");
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.ibgeApiService.getLocalidadesWithoutCache());
        }
        catch (Exception e) {
            final String msg = "Error while getting the JSON Localidades without cache";
            log.error(msg);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(msg);
        }
    }

    /**
     * Request to IBGE's API the Localidades with caching and return the response as JSON
     * @return ResponseEntity
     */
    @GetMapping(value="localidades/cache/json")
    public ResponseEntity getLocalidadesWithCache() {
        log.debug("JSONController.getLocalidadesWithCache");
        log.info("localidades/cache/json");
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.ibgeApiService.getLocalidadesWithCache());
        }
        catch (Exception e) {
            final String msg = "Error while getting the JSON Localidades with cache";
            log.error(msg);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(msg);
        }
    }

}
