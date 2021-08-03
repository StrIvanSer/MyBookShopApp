package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetailsService;
import com.example.MyBookShopApp.services.BookReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Admin controller
 *
 * @author Иван Стрельцов
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookstoreUserDetailsService service;

    private final BookReviewService bookReviewService;

    public AdminController(BookstoreUserDetailsService service, BookReviewService bookReviewService) {
        this.service = service;
        this.bookReviewService = bookReviewService;
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", service.getAllUser());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/removeReview/{reviewID}/{slug}")
    public String removeReview(@PathVariable("reviewID") Integer id, @PathVariable("slug") String slug, Model model) {
        bookReviewService.removeReview(id);
        model.addAttribute("users", service.getAllUser());
        return "redirect:/books/" + slug;
    }


}
