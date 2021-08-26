package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BookstoreUserDto;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetailsService;
import com.example.MyBookShopApp.secutiry.BookstoreUserRegister;
import com.example.MyBookShopApp.services.BookReviewService;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private final BookstoreUserRegister userRegister;
    private final BookService bookService;

    @Autowired
    public AdminController(BookstoreUserDetailsService service, BookReviewService bookReviewService, BookstoreUserRegister userRegister, BookService bookService) {
        this.service = service;
        this.bookReviewService = bookReviewService;
        this.userRegister = userRegister;
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", service.getAllUser());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/userProfile/{id:\\d+}")
    public String editUser(@PathVariable Integer id, Model model) {
        model.addAttribute("curUsr", userRegister.getUserDtoById(id));
        return "admin/userEdit";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/removeReview/{reviewID}/{slug}")
    public String removeReview(@PathVariable("reviewID") Integer id, @PathVariable("slug") String slug, Model model) {
        bookReviewService.removeReview(id);
        model.addAttribute("users", service.getAllUser());
        return "redirect:/books/" + slug;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/removeBook/{id}")
    public String removeBook(@PathVariable("id") Integer id) {
        bookService.removeBook(id);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/userEditByAdmin/{id:\\d+}")
    public String editProfileByAdmin(
            @PathVariable Integer id,
            BookstoreUserDto userDto,
            Model model) {
        model = userRegister.editProfileByAdmin(userDto, id, model);
        model.addAttribute("curUsr", userRegister.getUserDtoById(id));
        return "admin/userEdit";
    }


}
