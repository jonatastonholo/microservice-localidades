package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import com.microservices.localidades.model.Municipio;
import com.microservices.localidades.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Request to IBGE's API the Localidades without caching and return a JSON as response
     * @return ResponseEntity
     */
    @GetMapping(value = "localidades/id/{nomeCidade}")
    public ResponseEntity getNomeCidadeWithoutCache(@PathVariable String nomeCidade) {
        log.debug("NomeCidadeController.getNomeCidadeWithoutCache");
        log.info("localidades/id/" + nomeCidade);
        try {
            return ResponseEntity.ok(getMunicipioId(nomeCidade, false));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Request to IBGE's API the Localidades with caching and return the response as JSON
     * @return ResponseEntity
     */
    @GetMapping(value="localidades/cache/id/{nomeCidade}")
    public ResponseEntity getNomeCidadeWithCache(@PathVariable String nomeCidade) {
        log.debug("NomeCidadeController.getNomeCidadeWithCache");
        log.info("localidades/cache/id/" + nomeCidade);
        try {
            return ResponseEntity.ok(getMunicipioId(nomeCidade, true));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Find city by name and return it's id
     * @param nome
     * @param withCache
     * @return The city id or exception if the city was not found
     * @throws Exception
     */
    private Long getMunicipioId(String nome, boolean withCache) throws Exception {
        final List<Municipio> municipios =
                withCache
                ? this._IBGEApiService.getCitiesOnlyWithCache()
                : this._IBGEApiService.getCitiesOnlyWithoutCache();
        final Municipio municipio = filterMunicipio(municipios,nome);
        if(municipio == null) throw new Exception("City not found");
        return municipio.getId();
    }

    /**
     * Get the city by name
     * @param municipios
     * @param name
     * @return
     */
    private Municipio filterMunicipio(List<Municipio> municipios, String name) {
        return municipios.stream()
                .filter(m -> StringUtils.normalizeString(name).equals(StringUtils.normalizeString(m.getNome())))
                .findAny()
                .orElse(null);
    }
}
