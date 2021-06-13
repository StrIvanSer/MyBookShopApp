package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.data.book.RecentlyViewedBook;
import com.example.MyBookShopApp.repo.RecentlyViewedBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Service
public class RecentlyViewedBooksService {

    private final RecentlyViewedBooksRepository recentlyViewedBooksRepository;

    @Autowired
    public RecentlyViewedBooksService(RecentlyViewedBooksRepository recentlyViewedBooksRepository) {
        this.recentlyViewedBooksRepository = recentlyViewedBooksRepository;
    }

    public void addRecentlyViewedBooksToUser(Book book, BookstoreUser user) {
        RecentlyViewedBook recentlyViewedBook = recentlyViewedBooksRepository.findByUserAndBook(user, book);
        if (isNull(recentlyViewedBook)) {
            recentlyViewedBooksRepository.save(new RecentlyViewedBook(null, user, book, Timestamp.valueOf(LocalDateTime.now())));
        } else {
            recentlyViewedBook.setTime(Timestamp.valueOf(LocalDateTime.now()));
            recentlyViewedBooksRepository.save(recentlyViewedBook);
        }
    }

}
