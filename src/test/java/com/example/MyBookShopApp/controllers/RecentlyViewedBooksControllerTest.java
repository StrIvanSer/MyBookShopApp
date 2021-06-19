package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RecentlyViewedBooksController.class})
@ExtendWith(SpringExtension.class)
class RecentlyViewedBooksControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private RecentlyViewedBooksController recentlyViewedBooksController;

    @Test
    void testGetNextSearchPage() throws Exception {
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("https://example.org/example", "foo",
                "foo", "foo");
        BookstoreUserDetails obj = new BookstoreUserDetails(new BookstoreUser());
        MockHttpServletRequestBuilder paramResult = deleteResult.param("limit", String.valueOf(1));
        MockHttpServletRequestBuilder paramResult1 = paramResult.param("offset", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult1.param("user", String.valueOf(obj));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recentlyViewedBooksController).build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetRecently() throws Exception {
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("https://example.org/example", "foo",
                "foo", "foo");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("user",
                String.valueOf(new BookstoreUserDetails(new BookstoreUser())));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recentlyViewedBooksController).build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

