package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.book.BookReview;
import com.example.MyBookShopApp.repo.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {

  private final BookReviewRepository bookReviewRepository;

  @Autowired
  public BookReviewService(BookReviewRepository bookReviewRepository) {
    this.bookReviewRepository = bookReviewRepository;
  }

  public void saveReview(BookReview review) {
    bookReviewRepository.save(review);
  }

  public void removeReview(Integer id) {
    bookReviewRepository.deleteById(id);
  }
}

