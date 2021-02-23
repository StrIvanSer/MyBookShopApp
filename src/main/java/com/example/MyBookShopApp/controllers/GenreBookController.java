package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.GenreType;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.GenreService;
import com.example.MyBookShopApp.services.GenreTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GenreBookController {

    private final GenreService genreService;
    private final GenreTypeService genreTypeService;
    private final BookService bookService;

    public GenreBookController(GenreService genreService, GenreTypeService genreTypeService, BookService bookService) {
        this.genreService = genreService;
        this.genreTypeService = genreTypeService;
        this.bookService = bookService;
    }

    @GetMapping("/genres")
    public String getGenres(Model model) {
        model.addAttribute("typeGenres", genreTypeService.getGenreTypeData());
        model.addAttribute("genres", genreService.getGenreMap());
        return "genres/index";
    }

    @GetMapping("/genres/{genre}")
    public String getGenrePage(
            @PathVariable Genre genre,
            Model model
    ) {
        model.addAttribute("genre", genre);
        model.addAttribute("booksGenre", bookService.getAllBookByGenre(genre));
        return "genres/slug";
    }

    @GetMapping("/genres/type{genreType}")
    public String getAllGenresPage(
            @PathVariable GenreType genreType,
            Model model
    ) {
        model.addAttribute("genre", genreType.getType());
        model.addAttribute("booksGenre", bookService.getAllBookByGenreType(genreType));
        return "genres/slug_type";
    }

}
