package com.example.MyBookShopApp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class MainPageControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    MainPageControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void mainPageAccessTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().isOk());
    }

    //
    @Test
    public void accessOnlyAuthorizedPageFailTest() throws Exception {
        mockMvc.perform(get("/my"))//get запрос
                .andDo(print())//распечатка в консоль
                .andExpect(status().is3xxRedirection())//проверка статуса
                .andExpect(redirectedUrl("http://localhost/signin"));//проверка редиректа
    }
//
    @Test
    public void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin("/signin").user("skillbox@inbox.ru").password("123123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithUserDetails("skillbox@inbox.ru")
    public void testAuthenticatedAccessToProfilePage() throws Exception{
        mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(authenticated());
//                .andExpect(xpath("/html/body/header/div[1]/div/div/div[3]/div/a[4]/span[1]")
//                        .string("skillbox"));
    }

    @Test
    public void testSearchQuery() throws Exception {
        mockMvc.perform(get("/search/If These Walls Could Talk 2"))
                .andDo(print());
//                .andExpect(xpath("/html/body/div/div/main/div[2]/div/div[1]/div[2]/strong/a")
//                        .string("If These Walls Could Talk 2"));
    }

}