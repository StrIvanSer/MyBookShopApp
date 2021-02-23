package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.GenreType;
import com.example.MyBookShopApp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return bookRepository.findBooksByReleaseBetween(dateFrom, dateTo);
    }
//
    public List<Book> getPopularBooks() {
        return bookRepository.getPopularBooks();
    }

    public List<Book> getAllBookByGenre(Genre genre) {
        return bookRepository.findAllByGenre(genre);
    }

    public List<Book> getAllBookByGenreType(GenreType genreType) {
        return bookRepository.findAllByGenre_GenreType(genreType);
    }

}
