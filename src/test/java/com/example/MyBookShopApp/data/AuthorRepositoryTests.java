package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.book.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.AuthorRepository;
import com.example.MyBookShopApp.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AuthorRepositoryTests {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    void findBooksByAuthorFirstNameContaining() {
        Author author = authorRepository.findByFirstName("Zak");
        String authorName =  author.getFirstName();
        assertNotNull(author);
        assertEquals(authorName, "Zak");
    }

}