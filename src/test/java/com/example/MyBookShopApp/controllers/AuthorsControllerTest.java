package com.example.MyBookShopApp.controllers;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.MyBookShopApp.data.book.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.services.AuthorService;
import com.example.MyBookShopApp.services.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthorsController.class})
@ExtendWith(SpringExtension.class)
public class AuthorsControllerTest {
    @MockBean
    private AuthorService authorService;

    @Autowired
    private AuthorsController authorsController;

    @MockBean
    private BookService bookService;

    @Test
    public void testAuthorBooks() throws Exception {
        when(this.bookService.getBooksByAuthorId((Integer) any(), (Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));

        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");
        when(this.authorService.getAuthorsById((Integer) any())).thenReturn(author);
        when(this.authorService.getAuthorsInAlphabetOrder()).thenReturn(new HashMap<String, List<Author>>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/author/{authorId:\\d+}", 1);
        MockMvcBuilders.standaloneSetup(this.authorsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("author", "authorBooks", "authorsMap"))
                .andExpect(MockMvcResultMatchers.view().name("/books/author"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/books/author"));
    }

    @Test
    public void testAuthorsMap() {
        HashMap<String, List<Author>> stringListMap = new HashMap<String, List<Author>>(1);
        when(this.authorService.getAuthorsInAlphabetOrder()).thenReturn(stringListMap);
        Map<String, List<Author>> actualAuthorsMapResult = this.authorsController.authorsMap();
        assertSame(stringListMap, actualAuthorsMapResult);
        assertTrue(actualAuthorsMapResult.isEmpty());
        verify(this.authorService).getAuthorsInAlphabetOrder();
    }

    @Test
    public void testAuthorsPage() throws Exception {
        when(this.authorService.getAuthorsInAlphabetOrder()).thenReturn(new HashMap<String, List<Author>>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authors");
        MockMvcBuilders.standaloneSetup(this.authorsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("authorsMap"))
                .andExpect(MockMvcResultMatchers.view().name("/authors/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/authors/index"));
    }

    @Test
    public void testAuthorsPage2() throws Exception {
        when(this.authorService.getAuthorsInAlphabetOrder()).thenReturn(new HashMap<String, List<Author>>(1));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/authors");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.authorsController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("authorsMap"))
                .andExpect(MockMvcResultMatchers.view().name("/authors/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/authors/index"));
    }

    @Test
    public void testGetNextAuthorBooksPage() throws Exception {
        when(this.bookService.getBooksByAuthorId((Integer) any(), (Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        when(this.authorService.getAuthorsInAlphabetOrder()).thenReturn(new HashMap<String, List<Author>>(1));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/books/author/page/{id:\\d+}", 1);
        MockHttpServletRequestBuilder paramResult = getResult.param("limit", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("offset", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.authorsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"count\":0,\"books\":[]}")));
    }

    @Test
    public void testSlugPage() throws Exception {
        when(this.bookService.getBooksByAuthorId((Integer) any(), (Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));

        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");
        when(this.authorService.getAuthorsById((Integer) any())).thenReturn(author);
        when(this.authorService.getAuthorsInAlphabetOrder()).thenReturn(new HashMap<String, List<Author>>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authors/slug/{id:\\d+}", 1);
        MockMvcBuilders.standaloneSetup(this.authorsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("author", "authorBooks", "authorsMap", "size"))
                .andExpect(MockMvcResultMatchers.view().name("/authors/slug"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/authors/slug"));
    }
}

