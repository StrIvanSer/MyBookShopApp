package com.example.MyBookShopApp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.MyBookShopApp.data.book.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookReview;
import com.example.MyBookShopApp.data.book.Genre;
import com.example.MyBookShopApp.data.rating.RatingBook;
import com.example.MyBookShopApp.data.rating.RatingCount;
import com.example.MyBookShopApp.data.rating.RatingCountI;
import com.example.MyBookShopApp.data.tag.Tag;
import com.example.MyBookShopApp.data.file.BookFile;
import com.example.MyBookShopApp.repo.RatingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RatingService.class})
@ExtendWith(SpringExtension.class)
public class RatingServiceTest {
    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    @Test
    public void testSaveRating() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Pop");
        genre.setBookListByGenre(new ArrayList<Book>());
        genre.setGenreType(Genre.GenreType.EASY_READING);

        Author author = new Author();
        author.setLastName("sam");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("TDescription");
        author.add(Link.of("Href"));
        author.setFirstName("jones");

        Book book = new Book();
        book.setId(1);
        book.setTagList(new ArrayList<Tag>());
        book.setPrice(10.0);
        book.setIsBestseller(1);
        book.setPriceOld(1);
        book.setBookFileList(new ArrayList<BookFile>());
        book.setDescription("first Book 1998");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPubDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        book.setGenre(genre);
        book.setAuthor(author);
        book.setBookReviewList(new ArrayList<BookReview>());
        book.setSlug("book-u5r-197");
        book.setTitle("Dr");
        book.add(Link.of("Href"));
        book.setImage("Image");

        RatingBook ratingBook = new RatingBook();
        ratingBook.setThreeStar(1);
        ratingBook.setFiveStar(1);
        ratingBook.setId(1);
        ratingBook.setTwoStart(1);
        ratingBook.setBook(book);
        ratingBook.setOneStar(1);
        ratingBook.setFourStart(1);
        when(this.ratingRepository.save((RatingBook) any())).thenReturn(ratingBook);
        this.ratingService.saveRating(new RatingBook(), 42);
        verify(this.ratingRepository).save((RatingBook) any());
    }

    @Test
    public void testFindBookById() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Horror");
        genre.setBookListByGenre(new ArrayList<Book>());
        genre.setGenreType(Genre.GenreType.EASY_READING);

        Author author = new Author();
        author.setLastName("Leyla ");
        author.setBookList(new ArrayList<Book>());
        author.setId(1);
        author.setDescription("Русский писатель-сатирик и исполнитель собственных литературных произведений, киносценарист,");
        author.add(Link.of("Href"));
        author.setFirstName("Innocent");

        Book book = new Book();
        book.setId(1);
        book.setTagList(new ArrayList<Tag>());
        book.setPrice(10.0);
        book.setIsBestseller(1);
        book.setPriceOld(1);
        book.setBookFileList(new ArrayList<BookFile>());
        book.setDescription("Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPubDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        book.setGenre(genre);
        book.setAuthor(author);
        book.setBookReviewList(new ArrayList<BookReview>());
        book.setSlug("book-utr-197");
        book.setTitle("Greenfingers");
        book.add(Link.of("Href"));
        book.setImage("Image");

        RatingBook ratingBook = new RatingBook();
        ratingBook.setThreeStar(1);
        ratingBook.setFiveStar(1);
        ratingBook.setId(1);
        ratingBook.setTwoStart(1);
        ratingBook.setBook(book);
        ratingBook.setOneStar(1);
        ratingBook.setFourStart(1);
        when(this.ratingRepository.findByBookId((Integer) any())).thenReturn(ratingBook);
        assertSame(ratingBook, this.ratingService.findBookById(11));
        verify(this.ratingRepository).findByBookId((Integer) any());
    }

    @Test
    public void testGetTotalAndAvgStars() {
        RatingCountI ratingCountI = mock(RatingCountI.class);
        when(ratingCountI.getAverage()).thenReturn(1);
        when(ratingCountI.getTotal()).thenReturn(1);
        when(this.ratingRepository.getTotalAndAvgStars((Integer) any())).thenReturn(ratingCountI);
        RatingCount actualTotalAndAvgStars = this.ratingService.getTotalAndAvgStars(123);
        assertEquals(1, actualTotalAndAvgStars.getAverage().intValue());
        assertEquals(1, actualTotalAndAvgStars.getTotal().intValue());
        verify(ratingCountI).getAverage();
        verify(ratingCountI).getTotal();
        verify(this.ratingRepository).getTotalAndAvgStars((Integer) any());
    }
}

