package com.example.MyBookShopApp.controllers.user_action;


import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
//            String postponedContents,
            Model model) {
        List<Book> books = bookRepository.getPostponedBooks(1);
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
    public String handleRemoveBookFromCart(@PathVariable("slug") String slug
//                                           @CookieValue(name = "postponedContents", required = false) String postponedContents,
//                                           HttpServletResponse response,
//                                           Model model
    ) {
        Book book = bookRepository.findBookBySlug(slug);
            bookService.removeFromPostpone(book);
        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/postpone/{slug}")
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug) {
        Book book = bookRepository.findBookBySlug(slug);
        if (bookRepository.getPostponedBooks(1).contains(book)) {
            return "redirect:/books/" + slug;
        } else {
            bookService.appendToPostpone(book);
        }
        return "redirect:/books/" + slug;

    }
}
