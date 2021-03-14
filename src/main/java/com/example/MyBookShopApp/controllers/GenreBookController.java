package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.data.book.Genre;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.MyBookShopApp.data.book.Genre.*;

@Controller
public class GenreBookController extends BaseMainModelAttributeController {

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
        model.addAttribute("booksGenre", bookService.getPageBookByGenre(genre, 0, 5).getContent());
        return "genres/slug";
    }

    @GetMapping("/genres/type{genreType}")
    public String getAllGenresPage(
            @PathVariable GenreType genreType,
            Model model
    ) {
        model.addAttribute("type", genreType);
        model.addAttribute("booksGenre", bookService.getPageBookByGenreType(genreType, 0, 5).getContent());
        return "genres/slug_type";
    }

    @GetMapping("/books/genre/{genreId:\\d+}")
    @ResponseBody
    public BooksPageDto getNextSearchPageGenre(
            @PathVariable Integer genreId,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto( bookService.getPageBookByGenreId(genreId, offset, limit).getContent());
    }

    @GetMapping("/books/genreType/{genreTypeValue}")
    @ResponseBody
    public BooksPageDto getNextSearchPageGenreType(
            @PathVariable String genreTypeValue,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto( bookService.getPageBookByGenreType(GenreType.valueOf(genreTypeValue), offset, limit).getContent());
    }

}
