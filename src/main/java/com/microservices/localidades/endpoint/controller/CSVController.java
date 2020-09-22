package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import com.microservices.localidades.util.CSVUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author Jônatas Tonholo
 *
 * Microservice Endpoint for get CSV from IBGE's REST API of "Localidades" (Locations)
 */
@RestController
@Slf4j
public class CSVController {
    @Autowired
    private IBGEApiService _IBGEApiService;

    /**
     * Request to IBGE's API the Localidades without caching and return a CSV file as response
     * @return OutputStream
     */
    @GetMapping(value = "localidades/csv")
    public OutputStream getLocalidadesWithoutCache(HttpServletResponse response) {
        log.debug("CSVController.getLocalidadesWithoutCache");
        log.info("localidades/csv");
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=localidades.csv");
            return CSVUtils.getCsvResponse(response, this._IBGEApiService.getLocalidadesWithoutCache());
        }
        catch (Exception e) {
            final String msg = "Error while getting the CSV of Localidades without cache";
            log.error(msg);
            return null; // FIXME: Improve that!
        }
    }

    /**
     * Request to IBGE's API the Localidades with caching and return a CSV file as response
     * @return OutputStream
     */
    @GetMapping(value="localidades/cache/csv")
    public OutputStream getLocalidadesWithCache(HttpServletResponse response) {
        log.debug("CSVController.getLocalidadesWithCache");
        log.info("localidades/cache/csv");
        try{
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=localidades.csv");
            return CSVUtils.getCsvResponse(response, this._IBGEApiService.getLocalidadesWithCache());
        }
        catch (Exception e) {
            final String msg = "Error while getting the CSV of Localidades with cache";
            log.error(msg);
            return null; // FIXME: Improve that!
        }
    }
}
