package com.example.MyBookShopApp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {HTTPController.class})
@ExtendWith(SpringExtension.class)
public class HTTPControllerTest {
    @Autowired
    private HTTPController hTTPController;

    @Test
    public void testGet500() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/500");
        MockMvcBuilders.standaloneSetup(this.hTTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recent"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/recent"));
    }


    @Test
    public void testGetAuthors() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authors/index.html");
        MockMvcBuilders.standaloneSetup(this.hTTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/authors"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/authors"));
    }


    @Test
    public void testGetDocumentsPage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/my.html");
        MockMvcBuilders.standaloneSetup(this.hTTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/my"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/my"));
    }


    @Test
    public void testGetDocumentsSlugPage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/documents/slug.html");
        MockMvcBuilders.standaloneSetup(this.hTTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/documents/slug"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/documents/slug"));
    }


    @Test
    public void testGetIndex() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/index.html");
        MockMvcBuilders.standaloneSetup(this.hTTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void testGetPopular() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/popular.html");
        MockMvcBuilders.standaloneSetup(this.hTTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/popular"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/popular"));
    }

    @Test
    public void testGetResent() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/recent.html");
        MockMvcBuilders.standaloneSetup(this.hTTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recent"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/recent"));
    }

}

