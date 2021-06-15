package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static java.util.Objects.isNull;

@Controller
public class PopularBooksController {

    private final BookService bookService;

    @Autowired
    public PopularBooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/popular")
    public String getPopular(Model model, @AuthenticationPrincipal BookstoreUserDetails user) {
        model.addAttribute("popularBooks", isNull(user) ? bookService.getPageOfPopularBooks(0, 5).getContent():
                bookService.getPageOfPopularBooksWithActiveUser(0, 6, user.getBookstoreUser().getId()).getContent());
        return "/books/popular";
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getNextSearchPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @AuthenticationPrincipal BookstoreUserDetails user
    ) {
        if (isNull(user)) return new BooksPageDto(bookService.getPageOfPopularBooks(offset, limit).getContent());
        return new BooksPageDto(bookService
                .getPageOfPopularBooksWithActiveUser(0, 6, user.getBookstoreUser().getId()).getContent());
    }

}
