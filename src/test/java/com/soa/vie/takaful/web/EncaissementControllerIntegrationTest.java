package com.soa.vie.takaful.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.soa.vie.takaful.repositories.IEncaissementRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateEncaissement;
import com.soa.vie.takaful.requestmodels.UserSingInRequestModel;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EncaissementControllerIntegrationTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private IEncaissementRepository iEncaissementRepository;

        private static String token;
        private CreateAndUpdateEncaissement createAndUpdateEncaissement;
        private static String encaissement_id;

        @BeforeAll
        void setUp() throws Exception {

                createAndUpdateEncaissement = new CreateAndUpdateEncaissement();

                UserSingInRequestModel userSingInRequestModel = new UserSingInRequestModel("moaiim@email.com",
                                "123456");

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/public/login").with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSingInRequestModel))
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                token = result.getResponse().getHeader("Authorization");
        }

        @Test
        @Order(1)
        void ajouterUnEncaissement() throws Exception {

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/api/private/addEncaissement")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(createAndUpdateEncaissement))
                                                .accept(MediaType.APPLICATION_JSON).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                encaissement_id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

                Assert.assertTrue(iEncaissementRepository.findById(encaissement_id).isPresent());
        }

        @Test
        @Order(2)
        void recevoirEncaissementParCotisation() throws Exception {

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.get("/api/private/encaissement/cotisation/1")
                                                .contentType(MediaType.APPLICATION_JSON).with(csrf())
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
                String json = result.getResponse().getContentAsString();
                DocumentContext context = JsonPath.parse(json);
                int length = context.read("$.length()");

                assertEquals(1, length);
        }

}