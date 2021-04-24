package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTests {

    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void findBooksByAuthorFirstNameContaining() {
        String authorName = "Noami";
        List<Book> bookListByAuthorFirstName = bookRepository.findBooksByAuthorFirstNameContaining(authorName);

        assertNotNull(bookListByAuthorFirstName);
        assertFalse(bookListByAuthorFirstName.isEmpty());

        for (Book book : bookListByAuthorFirstName){
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getAuthor().getFirstName()).contains(authorName);
        }
    }

    @Test
    void findBooksByTitleContaining() {
        String token = "fish";
        List<Book> bookListByTitleContaining = bookRepository.findBooksByTitleContaining(token);
        assertNotNull(bookListByTitleContaining);
        assertFalse(bookListByTitleContaining.isEmpty());
        for (Book book : bookListByTitleContaining){
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getTitle()).contains(token);
        }
    }

    @Test
    void getBestsellers() {
        List<Book> bestSellersBooks = bookRepository.getBestsellers();
        assertNotNull(bestSellersBooks);
        assertFalse(bestSellersBooks.isEmpty());
        assertThat(bestSellersBooks.size()).isGreaterThan(1);
    }

    @Test
    void getBooksByTitle(){
        List<Book> bookByTitle = bookRepository.findBooksByTitleContaining("S");
        assertNotNull(bookByTitle);
        assertFalse(bookByTitle.isEmpty());
        assertThat(bookByTitle.size()).isGreaterThan(1);

    }
}