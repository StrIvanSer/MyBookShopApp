package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Genre;

import com.example.MyBookShopApp.data.GenreType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GenreRepo extends JpaRepository<Genre, Integer> {

    List<Genre> findAllByGenreType(GenreType genreType);

}
