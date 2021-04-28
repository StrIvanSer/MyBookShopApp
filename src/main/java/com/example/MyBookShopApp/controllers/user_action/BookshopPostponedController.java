package com.example.MyBookShopApp.controllers.user_action;


import com.example.MyBookShopApp.annotations.UserActionToPostponedLoggable;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.MyBookShopApp.data.book.Book2Type.TypeStatus.*;

@Controller
@RequestMapping("/books")
public class BookshopPostponedController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @Autowired
    public BookshopPostponedController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @ModelAttribute(name = "bookPostponed")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/postponed")
    public String getPostponed(
            @AuthenticationPrincipal BookstoreUserDetails user,
            Model model) {
        List<Book> books = bookRepository.getPostponedBooks(user.getBookstoreUser().getId());
        if (books.isEmpty()) {
            model.addAttribute("isPostponedEmpty", true);
            model.addAttribute("bookPostponed", new ArrayList<Book>());
        } else {
            model.addAttribute("isPostponedEmpty", false);
            model.addAttribute("bookPostponed", books);
        }
        return "postponed";
    }

    @PostMapping("/changeBookStatus/removePostpone/{slug}")
    @UserActionToPostponedLoggable
    public String handleRemoveBookFromCart(@PathVariable("slug") String slug,
                                           @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        bookService.removeFromBook2User(book, user.getBookstoreUser());
        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/postpone/{slug}")
    @UserActionToPostponedLoggable
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        if (bookService.getPostponedBooks(user.getBookstoreUser().getId()).contains(book)) {
            return "redirect:/books/" + slug;
        } else {
            bookService.saveBook2User(book, user.getBookstoreUser(), KEPT);
        }
        return "redirect:/books/" + slug;

    }
}
