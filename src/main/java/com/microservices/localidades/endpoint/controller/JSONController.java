package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import com.microservices.localidades.model.Localidade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private IBGEApiService _IBGEApiService;

    /**
     * Request to IBGE's API the Localidades and return a JSON as response
     * @return ResponseEntity
     */
    @GetMapping(value = "localidades/json")
    public ResponseEntity getLocalidadesWithCache() {
        log.debug("JSONController.getLocalidadesWithCache");
        log.info("localidades/json");
        try {
            List<Localidade> localidades = this._IBGEApiService.getLocalidades();
            log.info("Responding ok");
            return ResponseEntity.ok(localidades);
        }
        catch (Exception e) {
            String msg = "Error while getting the JSON Localidades";
            log.error(msg);
            return ResponseEntity.ok(msg);
        }
    }

    /**
     * Request to IBGE's API the Localidades without caching and return the response as JSON
     * @return ResponseEntity
     */
    @GetMapping(value="localidades/json/withoutCache")
    public ResponseEntity getLocalidadesWithoutCache() {
        log.debug("JSONController.getLocalidadesWithoutCache");
        log.info("localidades/json/withoutCache");
        List<Localidade> localidades = this._IBGEApiService.getLocalidadesWithNoCache();
        return ResponseEntity.ok(localidades);
    }

}
