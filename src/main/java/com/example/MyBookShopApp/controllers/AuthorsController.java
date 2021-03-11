package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.services.AuthorService;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AuthorsController extends BaseMainModelAttributeController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorsController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        return authorService.getAuthorsInAlphabetOrder();
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "/authors/index";
    }

    @GetMapping("/authors/slug/{id:\\d+}")
    public String slugPage(@PathVariable Integer id, Model model) {
        Page<Book> bookPage = bookService.getBooksByAuthorId(id, 0, 6);
        model.addAttribute("author", authorService.getAuthorsById(id));
        model.addAttribute("authorBooks", bookPage.getContent());
        model.addAttribute("size", bookPage.getTotalElements());
        return "/authors/slug";
    }

    @GetMapping(value = "/books/author/page/{id:\\d+}")
    @ResponseBody
    public BooksPageDto getNextAuthorBooksPage(
            @PathVariable Integer id,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto( bookService.getBooksByAuthorId(id, offset, limit).getContent());
    }

    @GetMapping("/books/author/{authorId:\\d+}")
    public String authorBooks(@PathVariable Integer authorId, Model model) {
        model.addAttribute("author", authorService.getAuthorsById(authorId));
        model.addAttribute("authorBooks", bookService.getBooksByAuthorId(authorId, 0, 5).getContent());
        return "/books/author";
    }

//    @GetMapping("/api/authors")
//    @ResponseBody
//    public Map<String, List<Author>> authors() {
//        return authorService.getAuthorsInAlphabetOrder();
//    }

}
