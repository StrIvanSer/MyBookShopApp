package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.BookReview;
import com.example.MyBookShopApp.data.book.BookReviewLike;
import com.example.MyBookShopApp.data.book.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepo extends JpaRepository<BookReviewLike, Integer> {

    BookReviewLike findByUserAndAndBookReview(BookstoreUser user, BookReview bookReview);

}