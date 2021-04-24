package com.example.MyBookShopApp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DocumentsController.class})
@ExtendWith(SpringExtension.class)
public class DocumentsControllerTest {
    @Autowired
    private DocumentsController documentsController;

    @Test
    public void testDocumentPage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/documents");
        MockMvcBuilders.standaloneSetup(this.documentsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("/documents/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/documents/index"));
    }

    @Test
    public void testDocumentsSlugPage() {
        assertEquals("/documents/slug", (new DocumentsController()).documentsSlugPage());
    }

    @Test
    public void testDocumentPage2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/documents", "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.documentsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("/documents/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/documents/index"));
    }
}

