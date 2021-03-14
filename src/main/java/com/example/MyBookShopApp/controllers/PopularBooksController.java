package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book.BooksPageDto;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PopularBooksController extends BaseMainModelAttributeController {

    private final BookService bookService;

    @Autowired
    public PopularBooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/popular")
    public String getPopular(Model model) {
        model.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 5).getContent());
        return "/books/popular";
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getNextSearchPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto(bookService.getPageOfPopularBooks(offset, limit).getContent());
    }

}
