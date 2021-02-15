package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByReleaseBetween( Date dateFrom, Date dateTo);

    @Query(value = "SELECT * FROM books AS b JOIN rating_books AS rb ON rb.id = b.rating_books_id WHERE rb.rating > 7 " , nativeQuery = true)
    List<Book> getPopularBooks();
}
