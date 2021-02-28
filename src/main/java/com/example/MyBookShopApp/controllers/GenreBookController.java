package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.example.MyBookShopApp.data.Genre.*;

@Controller
public class GenreBookController {

    private final GenreService genreService;
    private final BookService bookService;

    public GenreBookController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/genres")
    public String getGenres(Model model) {
        model.addAttribute("genres", genreService.getGenreMap());
        return "genres/index";
    }

    @GetMapping("/genres/{genre}")
    public String getGenrePage(
            @PathVariable Genre genre,
            Model model
    ) {
        model.addAttribute("genres", genre);
        model.addAttribute("booksGenre", bookService.getAllBookByGenre(genre));
        return "genres/slug";
    }

    @GetMapping("/genres/type{genreType}")
    public String getAllGenresPage(
            @PathVariable GenreType genreType,
            Model model
    ) {
        model.addAttribute("type", genreType);
        model.addAttribute("booksGenre", bookService.getAllBookByGenreType(genreType));
        return "genres/slug_type";
    }

}
