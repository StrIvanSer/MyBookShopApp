package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.GenreType;
import com.example.MyBookShopApp.repo.GenreTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreTypeService {

    private final GenreTypeRepo genreTypeRepo;

    public GenreTypeService(GenreTypeRepo genreTypeRepo) {
        this.genreTypeRepo = genreTypeRepo;
    }

    public List<GenreType> getGenreTypeData() {
        return genreTypeRepo.findAll();
    }
}
