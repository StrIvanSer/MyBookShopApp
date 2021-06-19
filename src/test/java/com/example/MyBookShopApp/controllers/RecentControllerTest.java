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

@ContextConfiguration(classes = {RecentController.class})
@ExtendWith(SpringExtension.class)
public class RecentControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private RecentController recentController;

    @Test
    void testGetNextSearchPage() throws Exception {
        when(this.bookService.getPageOfRecentBooksData((java.util.Date) any(), (java.util.Date) any(), (Integer) any(),
                (Integer) any())).thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/books/recent/page").param("from", "foo");
        MockHttpServletRequestBuilder paramResult1 = paramResult.param("limit", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult1.param("offset", String.valueOf(1)).param("to", "foo");
        MockMvcBuilders.standaloneSetup(this.recentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"count\":0,\"books\":[]}")));
    }

    @Test
    void testGetRecent() throws Exception {
        when(this.bookService.getPageOfRecentBooksData((java.util.Date) any(), (java.util.Date) any(), (Integer) any(),
                (Integer) any())).thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recent");
        MockMvcBuilders.standaloneSetup(this.recentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dateFrom", "dateTo", "recentBooks"))
                .andExpect(MockMvcResultMatchers.view().name("/books/recent"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/books/recent"));
    }

    @Test
    void testGetRecent2() throws Exception {
        when(this.bookService.getPageOfRecentBooksData((java.util.Date) any(), (java.util.Date) any(), (Integer) any(),
                (Integer) any())).thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/recent");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.recentController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dateFrom", "dateTo", "recentBooks"))
                .andExpect(MockMvcResultMatchers.view().name("/books/recent"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/books/recent"));
    }
}

