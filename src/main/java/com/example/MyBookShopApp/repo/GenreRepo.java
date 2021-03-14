package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Genre;


import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepo extends JpaRepository<Genre, Integer> {

}
