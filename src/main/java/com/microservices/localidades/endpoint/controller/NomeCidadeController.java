package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import com.microservices.localidades.model.Municipio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jônatas Tonholo
 *
 * Microservice Endpoint for get a City's id from IBGE's REST API of "Localidades" (Locations)
 */
@RestController
@Slf4j
public class NomeCidadeController {
    @Autowired
    private IBGEApiService _IBGEApiService;

    /**
     * Request to IBGE's API the Localidades and return a JSON as response
     * @return ResponseEntity
     */
    @GetMapping(value = "localidades/id/{nomeCidade}")
    public ResponseEntity getNomeCidadeWithCache(@PathVariable String nomeCidade) {
        log.debug("NomeCidadeController.getNomeCidadeWithCache");
        log.info("localidades/id/" + nomeCidade);
        try {
            List<Municipio> municipios = this._IBGEApiService.getCitiesWithCache();
            Municipio municipio = filterMunicipio(municipios,nomeCidade);
            if(municipio == null) throw new Exception("City not found");
            log.info("Responding ok");
            return ResponseEntity.ok(municipio.getId());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Request to IBGE's API the Localidades without caching and return the response as JSON
     * @return ResponseEntity
     */
    @GetMapping(value="localidades/id/{nomeCidade}/withoutCache")
    public ResponseEntity getNomeCidadeWithoutCache(@PathVariable String nomeCidade) {
        log.debug("NomeCidadeController.getNomeCidadeWithoutCache");
        log.info("localidades/id/" + nomeCidade);
        try {
            List<Municipio> municipios = this._IBGEApiService.getCitiesWithoutCache();
            Municipio municipio = filterMunicipio(municipios,nomeCidade);
            if(municipio == null) throw new Exception("City not found");
            log.info("Responding ok");
            return ResponseEntity.ok(municipio.getId());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Get the city by name
     * @param municipios
     * @param name
     * @return
     */
    private Municipio filterMunicipio(List<Municipio> municipios, String name) {
        return municipios.stream()
                .filter(m -> name.toLowerCase().equals(m.getNome().toLowerCase()))
                .findAny()
                .orElse(null);
    }
}
