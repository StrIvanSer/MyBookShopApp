package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.RatingBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingBook, Integer> {

}
