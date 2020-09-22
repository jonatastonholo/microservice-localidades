package com.microservices.localidades.endpoint.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(JSONController.class)
class JSONControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    void getJSONLocalidades() {
//        try {
//
//            RequestBuilder request = MockMvcRequestBuilders.get("localidades/json");
//            MvcResult result = mvc.perform(request).andReturn();
//            String resultStr = result.getResponse().getContentAsString();
//            assertNotEquals("Erro ao obter JSON de localidades", resultStr);
//        } catch (Exception e) {
//            assert false;
//        }
//
//    }
}