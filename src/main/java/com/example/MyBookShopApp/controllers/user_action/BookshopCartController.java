package com.example.MyBookShopApp.controllers.user_action;


import com.example.MyBookShopApp.annotations.UserActionToCartLoggable;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.MyBookShopApp.data.book.Book2Type.TypeStatus.CART;

@Controller
@RequestMapping("/books")
public class BookshopCartController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    private final BookService bookService;

    @Autowired
    public BookshopCartController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/cart")
    public String handleCartRequest(
            Model model,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        int cost = 0;
        int oldCost = 0;
        List<Book> books = bookService.getCartBooks(user.getBookstoreUser().getId());
        if (books.isEmpty()) {
            model.addAttribute("isCartEmpty", true);
            model.addAttribute("cost", cost);
        } else {
            for (Book booksFromCookieSlug : books) {
                cost = cost + booksFromCookieSlug.discountPrice();
                oldCost = oldCost + booksFromCookieSlug.getPriceOld();
            }
            model.addAttribute("cost", cost);
            model.addAttribute("oldCost", oldCost);
            model.addAttribute("bookCart", books);
        }
//        }

        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    @UserActionToCartLoggable
    public String handleRemoveBookFromCartRequest(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        bookService.removeFromBook2User(book, user.getBookstoreUser());
        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    @UserActionToCartLoggable
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        if (bookService.getCartBooks(user.getBookstoreUser().getId()).contains(book)) {
            return "redirect:/books/" + slug;
        } else {
            bookService.saveBook2User(book, user.getBookstoreUser(), CART);
        }

        return "redirect:/books/" + slug;
    }
}
