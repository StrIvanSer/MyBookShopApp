package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.book.BookReview;
import com.example.MyBookShopApp.data.book.BookReviewLike;
import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.repo.BookReviewLikeRepo;
import com.example.MyBookShopApp.repo.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Objects.isNull;

@Service
public class BookReviewLikeService {

    private final BookReviewLikeRepo bookReviewLikeRepo;
    private final BookReviewRepository reviewRepository;

    @Autowired
    public BookReviewLikeService(BookReviewLikeRepo bookReviewLikeRepo, BookReviewRepository reviewRepository) {
        this.bookReviewLikeRepo = bookReviewLikeRepo;
        this.reviewRepository = reviewRepository;
    }

    public BookReviewLike getBookReviewLikeByUserAndReview(BookstoreUser user, BookReview bookReview) {
        return bookReviewLikeRepo.findByUserAndAndBookReview(user, bookReview);
    }

    public Boolean saveReviewLike(BookstoreUser user, Integer bookReviewId, Short value) {
        BookReview bookReview = reviewRepository.getOne(bookReviewId);
        BookReviewLike reviewLike = getBookReviewLikeByUserAndReview(user, bookReview);
        if (isNull(reviewLike)) {
            reviewLike = new BookReviewLike();
            reviewLike.setUser(user);
            reviewLike.setBookReview(bookReview);
            reviewLike.setTime(new Date());
            reviewLike.setValue(value);
            bookReviewLikeRepo.save(reviewLike);
            return true;
        }
        reviewLike.setValue(value);
        reviewLike.setTime(new Date());
        bookReviewLikeRepo.save(reviewLike);
        return true;
    }


}
