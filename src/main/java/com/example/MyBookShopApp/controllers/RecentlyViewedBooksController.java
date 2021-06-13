package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RecentlyViewedBooksController {

    private final BookService bookService;

    @Autowired
    public RecentlyViewedBooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/recently_views")
    public String getPopular(Model model) {
        model.addAttribute("recentlyViewedBooks", bookService.getPageOfRecentlyViewedBooks(0, 5).getContent());
        return "/books/recently_views";
    }

    @GetMapping("/books/recently_views")
    @ResponseBody
    public BooksPageDto getNextSearchPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto(bookService.getPageOfRecentlyViewedBooks(offset, limit).getContent());
    }

}
