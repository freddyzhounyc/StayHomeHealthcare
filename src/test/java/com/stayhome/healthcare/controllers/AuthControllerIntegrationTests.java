package com.stayhome.healthcare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayhome.healthcare.TestDataUtil;
import com.stayhome.healthcare.domain.dto.auth.AuthResponse;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegisterDoesNotDuplicateUsersWithSameEmail() throws Exception {
        RegisterRequest registerRequest1 = TestDataUtil.createRegisterRequestA1();
        String json1 = objectMapper.writeValueAsString(registerRequest1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        // Same email, different username
        RegisterRequest registerRequest2 = TestDataUtil.createRegisterRequestA2();
        String json2 = objectMapper.writeValueAsString(registerRequest2);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testRegisterDoesNotDuplicateUsersWithSameUsername() throws Exception {
        RegisterRequest registerRequest1 = TestDataUtil.createRegisterRequestA1();
        String json1 = objectMapper.writeValueAsString(registerRequest1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        // Same username, different email
        RegisterRequest registerRequest3 = TestDataUtil.createRegisterRequestA3();
        String json3 = objectMapper.writeValueAsString(registerRequest3);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json3)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testRegisterRegistersNonRegisteredUser() throws Exception {
        RegisterRequest registerRequest = TestDataUtil.createRegisterRequestA1();
        String json = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(responseBody, AuthResponse.class);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/accounts/" + authResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", authResponse.getToken())) // send auth/jwt
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(registerRequest.getEmail())
        );
    }

}
