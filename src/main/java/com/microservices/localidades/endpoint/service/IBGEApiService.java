package com.microservices.localidades.endpoint.service;

import com.microservices.localidades.component.IBGELocalidadesWebClient;
import com.microservices.localidades.model.Localidade;
import com.microservices.localidades.model.Municipio;
import com.microservices.localidades.model.UF;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jônatas Tonholo
 * <p>
 * Service que realiza requisições HTTP à API de localidades do IBGE
 */
@Service
@Slf4j
public class IBGEApiService {
    @Autowired
    private WebClient _IBGELocalidadesWebClient;
    private final String uriMunicipios = "/estados/{UF}/municipios";
    private final String uriUFs = "/estados";

    /**
     * Get the UF list from IBGE REST API
     * @return List of UFs
     */
    @Cacheable("getUFs")
    public List<UF> getUFs() {
        log.debug("IBGEApiService.getUFs");
        log.info("Getting UFs...");
        try {
            Mono<ResponseEntity<List<UF>>> monoUFs = this._IBGELocalidadesWebClient
                    .method(HttpMethod.GET)
                    .uri(uriUFs)
                    .retrieve()
                    .toEntityList(UF.class);
            // Wait for the response to continue
            return monoUFs.block().getBody();
        }
        catch (Exception e) {
            String msg = "Error while request UFs from IBGE's API";
            log.error(msg);
            return null;
        }
    }

    /**
     * Get the 'Municipios' list of received UF from IBGE REST API
     * @return List of Municipios
     */
    @Cacheable("getMunicipios")
    public List<Municipio> getMunicipios(List<UF> ufs) {
        log.debug("IBGEApiService.getMunicipios");
        log.info("Getting 'municipios...'");
        List<Municipio> municipios = new ArrayList<Municipio>();
        try {
            for (UF uf : ufs) {
                if(uf == null) throw new Exception("Error while request 'municipios' from IBGE's API. UF is null!");
                log.debug("Getting 'municipios' from " + uf.getSigla());
                Mono<ResponseEntity<List<Municipio>>> monoMunicipios = this._IBGELocalidadesWebClient
                        .get()
                        .uri(uriMunicipios.replace("{UF}", uf.getSigla()))
                        .retrieve()
                        .toEntityList(Municipio.class);
                // Wait for the response to continue
                municipios.addAll(monoMunicipios.block().getBody());
            }
            return municipios;
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /***
     * Get the 'localidades' list from IBGE's API REST with caching
     * @return List with all 'localidades'
     */
    @Cacheable("getLocalidades")
    public List<Localidade> getLocalidades() {
        log.debug("IBGEApiService.getLocalidades");
        List<Localidade> localidades = new ArrayList<Localidade>();
        try {

            // Obtém a lista de UFs da API
            List<UF> ufs = getUFs();
            log.info("ok");
            List<Municipio> municipios = getMunicipios(ufs);
            log.info("ok");

            log.info("Converting municipios to localidade format");
            for(UF uf : ufs) {
                for (Municipio m : municipios) {
                    Localidade l = new Localidade(
                            uf.getId(),
                            uf.getSigla(),
                            uf.getRegiao().getNome(),
                            m.getNome(),
                            m.getMicrorregiao().getMesorregiao().getNome(),
                            m.getNome() + "/" + uf.getSigla()
                    );
                    localidades.add(l);
                }
            }
            log.info("Done!");

        } catch (Exception e) {
            log.error("Erro inesperado durante requisição ao servidor de Localidades");
        }
        return localidades;
    }

    /**
     * Clean the localidades cache
     *
     * @return success message to send to http request
     */
    @CacheEvict({"getCities","getLocalidades","getUFs","getMunicipios"})
    @Scheduled(fixedDelay = 3600000L) // Clear the cache after 60 min (3600000L ms)
    public String cleanLocalidadesCache() {
        log.debug("IBGEApiService.cleanLocalidadesCache");
        log.info("Cleanning UFs cache");
        String msg = "Cache cleaned";
        log.info(msg);
        return msg;
    }

    /**
     * Get the 'localidades' list from IBGE's API REST without caching
     *
     * @return List with all 'localidades'
     */
    @CacheEvict({"getCities","getLocalidades","getUFs","getMunicipios"})
    public List<Localidade> getLocalidadesWithNoCache() {
        log.debug("IBGEApiService.getLocalidadesWithNoCache");
        return getLocalidades();
    }

    @Cacheable("getCities")
    public List<Municipio> getCitiesWithCache() {
        log.debug("IBGEApiService.getCitiesWithCache");
        List<UF> ufs = getUFs();
        log.info("ok");
        List<Municipio> municipios = getMunicipios(ufs);
        log.info("ok");
        return municipios;
    }

    @Cacheable("getCities")
    @CacheEvict({"getCities","getLocalidades","getUFs","getMunicipios"})
    public List<Municipio> getCitiesWithoutCache() {
        log.debug("IBGEApiService.getCitiesWithoutCache");
        List<UF> ufs = getUFs();
        log.info("ok");
        List<Municipio> municipios = getMunicipios(ufs);
        log.info("ok");
        return municipios;
    }

}
