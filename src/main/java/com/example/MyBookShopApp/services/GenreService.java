package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.GenreType;
import com.example.MyBookShopApp.repo.GenreRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepo genreRepo;

    public GenreService(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    public Map<Integer, List<Genre>> getGenreMap() {
        List<Genre> genres = genreRepo.findAll();

        if (!genres.isEmpty()) {
            return genres.stream().collect(Collectors.groupingBy((Genre g) -> g.getGenreType().getId()));
        }
        return new HashMap<>();
    }
}
