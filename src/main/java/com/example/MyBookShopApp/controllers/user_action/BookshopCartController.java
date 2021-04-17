package com.example.MyBookShopApp.controllers.user_action;


import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static com.example.MyBookShopApp.data.book.Book2Type.TypeStatus.CART;
import static com.example.MyBookShopApp.data.book.Book2Type.TypeStatus.KEPT;
import static java.util.Objects.isNull;

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
//            @CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        int cost = 0;
        int oldCost = 0;

//        if (isNull(user)) {
//            if (cartContents == null || cartContents.equals("")) {
//                model.addAttribute("isCartEmpty", true);
//                model.addAttribute("cost", cost);
//            } else {
//                model.addAttribute("isCartEmpty", false);
//                cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
//                cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
//                String[] cookieSlugs = cartContents.split("/");
//                List<Book> booksFromCookieSlugs = bookService.findBooksBySlugIn(cookieSlugs);
//                for (Book booksFromCookieSlug : booksFromCookieSlugs) {
//                    cost = cost + booksFromCookieSlug.discountPrice();
//                    oldCost = oldCost + booksFromCookieSlug.getPriceOld();
//                }
//                model.addAttribute("cost", cost);
//                model.addAttribute("oldCost", oldCost);
//                model.addAttribute("bookCart", booksFromCookieSlugs);
//            }
//        } else {
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
    public String handleRemoveBookFromCartRequest(
            @PathVariable("slug") String slug,
            @CookieValue(name = "cartContents", required = false) String cartContents,
            HttpServletResponse response,
            @AuthenticationPrincipal BookstoreUserDetails user) {

//        if (isNull(user)) {
//            if (cartContents != null || !cartContents.equals("")) {
//                ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
//                cookieBooks.remove(slug);
//                Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//        } else {
            Book book = bookService.findBookBySlug(slug);
            bookService.removeFromBook2User(book, user.getBookstoreUser());
//        }
        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @CookieValue(name = "cartContents", required = false) String cartContents,
            HttpServletResponse response,
            Model model,
            @AuthenticationPrincipal BookstoreUserDetails user) {

        if (isNull(user)) {
            if (cartContents == null || cartContents.equals("")) {
                Cookie cookie = new Cookie("cartContents", slug);
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            } else if (!cartContents.contains(slug)) {
                StringJoiner stringJoiner = new StringJoiner("/");
                stringJoiner.add(cartContents).add(slug);
                Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }
        } else {
            Book book = bookService.findBookBySlug(slug);
            if (bookService.getCartBooks(user.getBookstoreUser().getId()).contains(book)) {
                return "redirect:/books/" + slug;
            } else {
                bookService.saveBook2User(book, user.getBookstoreUser(), CART);
            }
        }
        return "redirect:/books/" + slug;
    }
}
