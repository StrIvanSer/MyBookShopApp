package com.example.MyBookShopApp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.Genre;
import com.example.MyBookShopApp.repo.Book2TypeRepository;
import com.example.MyBookShopApp.repo.Book2UserRepository;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.repo.BookstoreUserRepository;
import com.example.MyBookShopApp.repo.GenreRepo;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.GenreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;

@ContextConfiguration(classes = {GenreBookController.class})
@ExtendWith(SpringExtension.class)
public class GenreBookControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private GenreBookController genreBookController;

    @MockBean
    private GenreService genreService;

    @Test
    public void testGetAllGenresPage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/genres/type{genreType}",
                Genre.GenreType.EASY_READING);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.genreBookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetGenrePage() {
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findAllByGenre((Genre) any(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        BookService bookService = new BookService(bookRepository, mock(Book2UserRepository.class));
        GenreBookController genreBookController = new GenreBookController(new GenreService(mock(GenreRepo.class)),
                bookService);
        Genre genre = new Genre();
        assertEquals("genres/slug", genreBookController.getGenrePage(genre, new ConcurrentModel()));
        verify(bookRepository).findAllByGenre((Genre) any(), (org.springframework.data.domain.Pageable) any());
    }

    @Test
    public void testGetGenrePage2() {
        BookService bookService = mock(BookService.class);
        when(bookService.getPageBookByGenre((Genre) any(), (Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        GenreBookController genreBookController = new GenreBookController(new GenreService(mock(GenreRepo.class)),
                bookService);
        Genre genre = new Genre();
        assertEquals("genres/slug", genreBookController.getGenrePage(genre, new ConcurrentModel()));
        verify(bookService).getPageBookByGenre((Genre) any(), (Integer) any(), (Integer) any());
    }

    @Test
    public void testGetGenres() throws Exception {
        when(this.genreService.getGenreMap()).thenReturn(new HashMap<Genre.GenreType, List<Genre>>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/genres");
        MockMvcBuilders.standaloneSetup(this.genreBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("genres"))
                .andExpect(MockMvcResultMatchers.view().name("genres/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("genres/index"));
    }

    @Test
    public void testGetNextSearchPageGenre() throws Exception {
        when(this.bookService.getPageBookByGenreId((Integer) any(), (Integer) any(), (Integer) any()))
                .thenReturn(new PageImpl<Book>(new ArrayList<Book>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/books/genre/{genreId:\\d+}", 1);
        MockHttpServletRequestBuilder paramResult = getResult.param("limit", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("offset", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.genreBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"count\":0,\"books\":[]}")));
    }
}

