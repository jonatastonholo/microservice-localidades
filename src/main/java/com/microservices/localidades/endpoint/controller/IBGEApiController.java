package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jônatas Tonholo
 *
 * A Rest controller for receive and answear HTTP RESTful requirements
 */
@RestController
@Getter
@Slf4j
public class IBGEApiController {
    @Autowired
    private IBGEApiService _IBGEApiService;

    /**
     * Clear the cache of Localidades from IBGEApiService
     * @return ResponseEntity
     */
    @GetMapping(value="localidades/cache/clear")
    public ResponseEntity clearCache() {
        log.debug("IBGEApiController.clearCacheLocalidades");
        log.info("localidades/cache/clear");
        return ResponseEntity.ok(this._IBGEApiService.cleanLocalidadesCache());
    }
}
