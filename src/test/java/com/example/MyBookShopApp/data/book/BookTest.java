package com.example.MyBookShopApp.data.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

public class BookTest {
    @Test
    public void testAuthorsFullName() {
        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");
        Book book = new Book();
        book.setAuthor(author);
        assertEquals("Jane Doe", book.authorsFullName());
    }
}

