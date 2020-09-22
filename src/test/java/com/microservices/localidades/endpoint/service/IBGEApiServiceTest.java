package com.microservices.localidades.endpoint.service;

import com.microservices.localidades.model.Municipio;
import com.microservices.localidades.model.Regiao;
import com.microservices.localidades.model.UF;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Jônatas Tonholo
 * The unit test for IBGEApiService
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IBGEApiServiceTest {

    @Autowired
    IBGEApiService ibgeApiService;

    @Test
    void getUFs() {
        try {
            final List<UF> ufs = ibgeApiService.getUFs();
            assert ufs != null;
        }
        catch (Exception e) {
            assert false;
        }
    }

    @Test
    void getMunicipios() {
        try {
            List<UF> ufs = new ArrayList<>();
            ufs.add(new UF("MG", new Regiao()));
            ufs.add(new UF("GO", new Regiao()));
            ufs.add(new UF("SP", new Regiao()));

            final List<Municipio> municipios = ibgeApiService.getMunicipios(ufs);
            assert municipios != null;
        }
        catch (Exception e) {
            assert false;
        }
    }
}