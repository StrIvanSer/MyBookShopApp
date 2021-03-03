package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

/**
 * Сервис для работы с данными класса Book
 *
 * @author Иван Стрельцов
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public Page<Book> getBooksByAuthorId(Integer authorId, Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.findByAuthorId(authorId, nextPage);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price) {
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPriceDiscount() {
        return bookRepository.getBookWithMaxDiscount();
    }

    public List<Book> getBestsellers() {
        return bookRepository.getBestsellers();
    }

    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    public Page<Book> getPageOfRecentBooksData(Date dateFrom, Date dateTo, Integer offset, Integer limit) {
        Pageable nextPage = of(offset, limit);
        return bookRepository.findPageOfBooksByPubDateBetweenOrderByPubDate(dateFrom, dateTo, nextPage);
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfPopularBooks(Integer page, Integer limit) {
        Pageable nextPage = of(page, limit);
        return bookRepository.getPageOfPopularBooks(nextPage);
    }

    public Page<Book> getPageBookByGenreType(Genre.GenreType genreType, Integer offset, Integer limit) {
        Pageable nextPage = of(offset, limit);
        return bookRepository.findAllByGenre_GenreType(genreType, nextPage);
    }

    public Page<Book> getPageBookByGenre(Genre genre, Integer offset, Integer limit) {
        Pageable nextPage = of(offset, limit);
        return bookRepository.findAllByGenre(genre, nextPage);
    }

    public Page<Book> getPageBookByGenreId(Integer genreId, Integer offset, Integer limit) {
        Pageable nextPage = of(offset, limit);
        return bookRepository.findAllByGenreId(genreId, nextPage);
    }

    public Page<Book> getBooksByTag(Integer tagId, Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.findBooksByTag(tagId, nextPage);
    }
}
