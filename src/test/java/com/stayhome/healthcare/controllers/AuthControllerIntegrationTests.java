package com.stayhome.healthcare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stayhome.healthcare.TestDataUtil;
import com.stayhome.healthcare.domain.dto.auth.AuthRequest;
import com.stayhome.healthcare.domain.dto.auth.AuthResponse;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import jakarta.servlet.http.Cookie;
import org.hamcrest.core.IsNull;
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
                        .cookie(result.getResponse().getCookie("token")) // send auth/jwt
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(registerRequest.getEmail())
        );
    }
    @Test
    public void testThatRegisterTrimsEmailAndUsername() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestB();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(request.getEmail().trim())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(request.getUsername().trim())
        );
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadEmailA() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadEmailA();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadEmailB() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadEmailB();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadEmailC() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadEmailC();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadPasswordA() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadPasswordA();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadPasswordB() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadPasswordB();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadPasswordC() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadPasswordC();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadPasswordD() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadPasswordD();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadPasswordE() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadPasswordE();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithABadPasswordF() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadPasswordF();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithBadUsernameA() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadUsernameA();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithBadUsernameB() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadUsernameB();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithBadUsernameC() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadUsernameC();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithBadUsernameD() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadUsernameD();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatRegisterDoesNotRegisterWithBadUsernameE() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestWithBadUsernameE();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testThatLoginWorksAfterRegisteringUser() throws Exception {
        // Create register request
        RegisterRequest request = TestDataUtil.createRegisterRequestA1();
        String json = objectMapper.writeValueAsString(request);

        // Step 1: Register new user
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        // Create login request
        AuthRequest loginRequest = TestDataUtil.createAuthRequestA1();
        String loginJson = objectMapper.writeValueAsString(loginRequest);

        // Step 2: Login
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testSuccessfulRegisterWillSetCookiesInClient() throws Exception {
        RegisterRequest request = TestDataUtil.createRegisterRequestA1();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.cookie().exists("token")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").doesNotExist()
        );
    }

}
