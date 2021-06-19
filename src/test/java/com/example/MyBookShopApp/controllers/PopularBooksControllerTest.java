package com.example.MyBookShopApp.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.services.BookService;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PopularBooksController.class})
@ExtendWith(SpringExtension.class)
public class PopularBooksControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private PopularBooksController popularBooksController;

    @Test
    void testGetNextSearchPage() throws Exception {
        when(this.bookService.getPageOfPopularBooks((Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/books/popular");
        MockHttpServletRequestBuilder paramResult = getResult.param("limit", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("offset", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.popularBooksController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"count\":0,\"books\":[]}")));
    }

    @Test
    public void testGetPopular() throws Exception {
        when(this.bookService.getPageOfPopularBooks((Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/popular");
        MockMvcBuilders.standaloneSetup(this.popularBooksController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("popularBooks"))
                .andExpect(MockMvcResultMatchers.view().name("/books/popular"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/books/popular"));
    }
}

