package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.data.book.RecentlyViewedBook;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecentlyViewedBooksRepository extends JpaRepository<RecentlyViewedBook, Integer> {

    RecentlyViewedBook findByUserAndBook(BookstoreUser bookstoreUser, Book book);

}