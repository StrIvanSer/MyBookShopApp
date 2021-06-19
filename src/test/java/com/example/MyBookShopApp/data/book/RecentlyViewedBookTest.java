package com.example.MyBookShopApp.data.book;

import com.example.MyBookShopApp.data.file.BookFile;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static org.junit.jupiter.api.Assertions.*;

public class RecentlyViewedBookTest {
    @Test
    public void testCanEqual() {
        assertFalse((new RecentlyViewedBook()).canEqual("other"));
    }

    @Test
    public void testEquals2() {
        Timestamp time = new Timestamp(10L);
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setTime(time);
        recentlyViewedBook.setId(1);
        Book book = new Book();
        book.setId(1);
        book.setTagList(new ArrayList<Tag>());
        book.setPrice(10.0);
        book.setIsBestseller(0);
        book.setPriceOld(0);
        book.setBookFileList(new ArrayList<BookFile>());
        book.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPubDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Name");
        genre.setBookListByGenre(new ArrayList<Book>());
        genre.setGenreType(Genre.GenreType.EASY_READING);
        book.setGenre(genre);
        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");
        book.setAuthor(author);
        book.setBookReviewList(new ArrayList<BookReview>());
        book.setSlug("Slug");
        book.setTitle("Dr");
        book.add(Link.of("Href"));
        book.setImage("Image");
        recentlyViewedBook.setBook(book);
        BookstoreUser bookstoreUser = new BookstoreUser();
        bookstoreUser.setEmail("jane.doe@example.org");
        bookstoreUser.setPassword("iloveyou");
        bookstoreUser.setId(1);
        bookstoreUser.setName("Name");
        bookstoreUser.setIsOAuth2(true);
        bookstoreUser.setPhone("4105551212");
        bookstoreUser.setIdOAuth("Id OAuth");
        recentlyViewedBook.setUser(bookstoreUser);
        assertNotEquals(recentlyViewedBook, (new RecentlyViewedBook()));
    }

    @Test
    public void testEquals3() {
        Timestamp time = new Timestamp(10L);
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setTime(time);
        recentlyViewedBook.setId(null);
        Book book = new Book();
        book.setId(1);
        book.setTagList(new ArrayList<Tag>());
        book.setPrice(10.0);
        book.setIsBestseller(0);
        book.setPriceOld(0);
        book.setBookFileList(new ArrayList<BookFile>());
        book.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPubDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Name");
        genre.setBookListByGenre(new ArrayList<Book>());
        genre.setGenreType(Genre.GenreType.EASY_READING);
        book.setGenre(genre);
        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");
        book.setAuthor(author);
        book.setBookReviewList(new ArrayList<BookReview>());
        book.setSlug("Slug");
        book.setTitle("Dr");
        book.add(Link.of("Href"));
        book.setImage("Image");
        recentlyViewedBook.setBook(book);
        BookstoreUser bookstoreUser = new BookstoreUser();
        bookstoreUser.setEmail("jane.doe@example.org");
        bookstoreUser.setPassword("iloveyou");
        bookstoreUser.setId(1);
        bookstoreUser.setName("Name");
        bookstoreUser.setIsOAuth2(true);
        bookstoreUser.setPhone("4105551212");
        bookstoreUser.setIdOAuth("Id OAuth");
        recentlyViewedBook.setUser(bookstoreUser);
        assertNotEquals(recentlyViewedBook, (new RecentlyViewedBook()));
    }

    @Test
    public void testEquals4() {
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setId(1);
        Timestamp time = new Timestamp(10L);
        RecentlyViewedBook recentlyViewedBook1 = new RecentlyViewedBook();
        recentlyViewedBook1.setTime(time);
        recentlyViewedBook1.setId(1);
        Book book = new Book();
        book.setId(1);
        book.setTagList(new ArrayList<Tag>());
        book.setPrice(10.0);
        book.setIsBestseller(0);
        book.setPriceOld(0);
        book.setBookFileList(new ArrayList<BookFile>());
        book.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPubDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Name");
        genre.setBookListByGenre(new ArrayList<Book>());
        genre.setGenreType(Genre.GenreType.EASY_READING);
        book.setGenre(genre);
        Author author = new Author();
        author.setLastName("Doe");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("The characteristics of someone or something");
        author.add(Link.of("Href"));
        author.setFirstName("Jane");
        book.setAuthor(author);
        book.setBookReviewList(new ArrayList<BookReview>());
        book.setSlug("Slug");
        book.setTitle("Dr");
        book.add(Link.of("Href"));
        book.setImage("Image");
        recentlyViewedBook1.setBook(book);
        BookstoreUser bookstoreUser = new BookstoreUser();
        bookstoreUser.setEmail("jane.doe@example.org");
        bookstoreUser.setPassword("iloveyou");
        bookstoreUser.setId(1);
        bookstoreUser.setName("Name");
        bookstoreUser.setIsOAuth2(true);
        bookstoreUser.setPhone("4105551212");
        bookstoreUser.setIdOAuth("Id OAuth");
        recentlyViewedBook1.setUser(bookstoreUser);
        assertNotEquals(recentlyViewedBook1, recentlyViewedBook);
    }

    @Test
    public void testHashCode() {
        assertEquals(21100921, (new RecentlyViewedBook()).hashCode());
    }

    @Test
    public void testHashCode2() {
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setId(1);
        assertEquals(12475003, recentlyViewedBook.hashCode());
    }

    @Test
    public void testHashCode3() {
        BookstoreUser bookstoreUser = new BookstoreUser();
        bookstoreUser.setEmail("jane.doe@example.org");
        bookstoreUser.setPassword("iloveyou");
        bookstoreUser.setId(1);
        bookstoreUser.setName(null);
        bookstoreUser.setIsOAuth2(true);
        bookstoreUser.setPhone("4105551212");
        bookstoreUser.setIdOAuth(null);
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setUser(bookstoreUser);
        assertEquals(-1500553085, recentlyViewedBook.hashCode());
    }

    @Test
    public void testHashCode4() {
        Timestamp time = new Timestamp(10L);
        BookstoreUser user = new BookstoreUser();
        assertEquals(758748593, (new RecentlyViewedBook(1, user, new Book(), time)).hashCode());
    }

    @Test
    public void testSetBook() {
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setBook(new Book());
        assertEquals("RecentlyViewedBook(id=null, user=null, book=links: [], time=null)", recentlyViewedBook.toString());
    }

    @Test
    public void testSetId() {
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setId(1);
        assertEquals(1, recentlyViewedBook.getId().intValue());
    }

    @Test
    public void testSetTime() {
        Timestamp time = new Timestamp(10L);
        RecentlyViewedBook recentlyViewedBook = new RecentlyViewedBook();
        recentlyViewedBook.setTime(time);
        assertEquals("RecentlyViewedBook(id=null, user=null, book=null, time=1970-01-01 05:00:00.01)",
                recentlyViewedBook.toString());
    }


    @Test
    public void testToString() {
        assertEquals("RecentlyViewedBook(id=null, user=null, book=null, time=null)", (new RecentlyViewedBook()).toString());
    }
}

