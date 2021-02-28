package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public List<Book> getRecentBooksData(Date dateFrom, Date dateTo) {
        return bookRepository.findBooksByPubDateBetween(dateFrom, dateTo);
    }

    //
    public List<Book> getPopularBooks() {
        return bookRepository.getPopularBooks();
    }

    public List<Book> getAllBookByGenre(Genre genre) {
        return bookRepository.findAllByGenre(genre);
    }

    public List<Book> getAllBookByGenreType(Genre.GenreType genreType) {
        return bookRepository.findAllByGenre_GenreType(genreType);
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findBooksByTitleContaining(title);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price){
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPriceDiscount(){
        return bookRepository.getBookWithMaxDiscount();
    }

    public List<Book> getBestsellers(){
        return bookRepository.getBestsellers();
    }

}
