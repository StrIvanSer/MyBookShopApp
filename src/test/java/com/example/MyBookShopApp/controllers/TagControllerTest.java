package com.example.MyBookShopApp.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.tag.Tag;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.TagService;

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

@ContextConfiguration(classes = {TagController.class})
@ExtendWith(SpringExtension.class)
public class TagControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private TagController tagController;

    @MockBean
    private TagService tagService;

    @Test
    public void testGetNextPage() throws Exception {
        when(this.bookService.getBooksByTag((Integer) any(), (Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/books/tag/{tagId:\\d+}", 1);
        MockHttpServletRequestBuilder paramResult = getResult.param("limit", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("offset", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"count\":0,\"books\":[]}")));
    }

    @Test
    public void testGetTag() throws Exception {
        Tag tag = new Tag();
        tag.setBookList(new ArrayList<Book>());
        tag.setId(1);
        tag.setName("JAVA");
        when(this.tagService.getTag((Integer) any())).thenReturn(tag);
        when(this.bookService.getBooksByTag((Integer) any(), (Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tags/{tagId:\\d+}", 1);
        MockMvcBuilders.standaloneSetup(this.tagController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("tag", "tagBooks"))
                .andExpect(MockMvcResultMatchers.view().name("tags/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("tags/index"));
    }
}

