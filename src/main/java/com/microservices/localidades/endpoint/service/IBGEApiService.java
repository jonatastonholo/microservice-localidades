package com.microservices.localidades.endpoint.service;

import com.microservices.localidades.exceptions.APILocalidadesException;
import com.microservices.localidades.model.EIBGEApiURI;
import com.microservices.localidades.model.Localidade;
import com.microservices.localidades.model.Municipio;
import com.microservices.localidades.model.UF;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jônatas Tonholo
 *
 * Service que realiza requisições HTTP à API de localidades do IBGE
 */
@Service
@Slf4j
public class IBGEApiService {
    @Autowired
    private WebClient webClientIBGELocalidades;


    /**
     * Get the UF list from IBGE REST API
     * @return List of UFs
     */
    @Cacheable("getUFs")
    public List<UF> getUFs() {
        log.debug("IBGEApiService.getUFs");
        log.info("Getting UFs...");
        try {
            final Mono<ResponseEntity<List<UF>>> monoUFs = this.webClientIBGELocalidades
                    .method(HttpMethod.GET)
                    .uri(EIBGEApiURI.UF.getUri())
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
        log.info("Getting 'municipios'...");
        final List<Municipio> municipios = new ArrayList<>();
        try {
            for (UF uf : ufs) {
                if(uf == null) throw new APILocalidadesException("Error while request 'municipios' from IBGE's API. UF is null!");
                log.debug("Getting 'municipios' from " + uf.getSigla());
                final Mono<ResponseEntity<List<Municipio>>> monoMunicipios = this.webClientIBGELocalidades
                        .get()
                        .uri(EIBGEApiURI.MUNICIPIOS.getUri().replace("{UF}", uf.getSigla()))
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
    @Cacheable("getLocalidadesWithCache")
    public List<Localidade> getLocalidadesWithCache() {
        log.debug("IBGEApiService.getLocalidadesWithCache");
        final List<Localidade> localidades = new ArrayList<>();
        try {

            // Obtém a lista de UFs da API
            final List<UF> ufs = getUFs();
            log.info("ok");
            final List<Municipio> municipios = getMunicipios(ufs);
            log.info("ok");

            log.info("Converting municipios to localidade format");
            for (Municipio m : municipios) {
                final UF uf = m.getMicrorregiao().getMesorregiao().getUf();
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

            log.info("Done!");

        } catch (Exception e) {
            log.error("Unexpected error while requesting the Locations server");
        }
        return localidades;
    }

    /**
     * Get the 'localidades' list from IBGE's API REST without caching
     *
     * @return List with all 'localidades'
     */
    @CacheEvict({"getCitiesOnlyWithCache","getLocalidadesWithCache","getUFs","getMunicipios"})
    public List<Localidade> getLocalidadesWithoutCache() {
        log.debug("IBGEApiService.getLocalidadesWithNoCache");
        return getLocalidadesWithCache();
    }

    /**
     * Clean the localidades cache
     *
     * @return success message to send to http request
     */
    @CacheEvict({"getCitiesOnlyWithCache","getLocalidadesWithCache","getUFs","getMunicipios"})
    public String cleanLocalidadesCache() {
        log.debug("IBGEApiService.cleanLocalidadesCache");
        log.info("Cleaning UFs cache");
        final String msg = "Cache cleaned";
        log.info(msg);
        return msg;
    }

    /**
     * Get a list of Municipios ("cities") from IBGE API Rest with caching
     * @return
     */
    @Cacheable("getCitiesOnlyWithCache")
    public List<Municipio> getCitiesOnlyWithCache() {
        log.debug("IBGEApiService.getCitiesOnlyWithCache");
        List<UF> ufs = getUFs();
        log.info("ok");
        final List<Municipio> municipios = getMunicipios(ufs);
        log.info("ok");
        return municipios;
    }

    /**
     * Get a list of Municipios ("cities") from IBGE API Rest without caching
     * FIXME: Not so good (CLEAN CODE: DRY!!): need to improve using better the caching system
     * @return
     */
    @CacheEvict({"getCitiesOnlyWithCache","getLocalidadesWithCache","getUFs","getMunicipios"})
    public List<Municipio> getCitiesOnlyWithoutCache() {
        log.debug("IBGEApiService.getCitiesWithoutCache");
        final List<UF> ufs = getUFs();
        log.info("ok");
        final List<Municipio> municipios = getMunicipios(ufs);
        log.info("ok");
        return municipios;
    }

}
