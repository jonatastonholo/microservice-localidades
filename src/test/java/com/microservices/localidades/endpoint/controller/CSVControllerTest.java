package com.microservices.localidades.endpoint.controller;

import com.microservices.localidades.endpoint.service.IBGEApiService;
import com.microservices.localidades.model.Localidade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jônatas Tonholo
 * The unit test for CSVController
 */
@WebMvcTest(CSVController.class)
class CSVControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBGEApiService ibgeApiService;

    @MockBean
    private CSVController csvController;

    @Test
    void getLocalidadesWithoutCache() {
        try {
            final List<Localidade> localidades = new ArrayList<Localidade>();
            Mockito.when(this.ibgeApiService.getLocalidadesWithoutCache()).thenReturn(localidades);
            final String url = "/localidades/csv";
            try {
                RequestBuilder request = MockMvcRequestBuilders.get(url);
                MvcResult result = mockMvc.perform(request)
                        .andReturn();
                assert result.getResponse().getStatus() == HttpServletResponse.SC_OK;
            }
            catch (Exception e) {
                assert false;
            }
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void getLocalidadesWithCache() {
        try {
            final List<Localidade> localidades = new ArrayList<Localidade>();
            Mockito.when(this.ibgeApiService.getLocalidadesWithCache()).thenReturn(localidades);
            final String url = "/localidades/cache/csv";
            try {
                RequestBuilder request = MockMvcRequestBuilders.get(url);
                MvcResult result = mockMvc.perform(request)
                        .andReturn();
                assert result.getResponse().getStatus() == HttpServletResponse.SC_OK;
            }
            catch (Exception e) {
                assert false;
            }
        } catch (Exception e) {
            assert false;
        }
    }
}