package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import com.microservices.localidades.util.CSVUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Jônatas Tonholo
 *
 * Microservice Endpoint for get CSV from IBGE's REST API of "Localidades" (Locations)
 */
@RestController
@Slf4j
public class CSVController {
    @Autowired
    private IBGEApiService ibgeApiService;

    /**
     * Request to IBGE's API the Localidades without caching and return a CSV file as response
     * @return OutputStream
     */
    @GetMapping(value = "localidades/csv")
    public ResponseEntity getLocalidadesWithoutCache(HttpServletResponse response) {
        log.debug("CSVController.getLocalidadesWithoutCache");
        log.info("localidades/csv");
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=localidades.csv");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(CSVUtils.getCsvResponse(response, this.ibgeApiService.getLocalidadesWithoutCache()));
        }
        catch (Exception e) {
            final String msg = "Error while getting the CSV of Localidades without cache";
            log.error(msg);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(msg);
        }
    }

    /**
     * Request to IBGE's API the Localidades with caching and return a CSV file as response
     * @return OutputStream
     */
    @GetMapping(value="localidades/cache/csv")
    public ResponseEntity getLocalidadesWithCache(HttpServletResponse response) {
        log.debug("CSVController.getLocalidadesWithCache");
        log.info("localidades/cache/csv");
        try{
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=localidades.csv");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(CSVUtils.getCsvResponse(response, this.ibgeApiService.getLocalidadesWithCache()));
        }
        catch (Exception e) {
            final String msg = "Error while getting the CSV of Localidades with cache";
            log.error(msg);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(msg);
        }
    }
}
