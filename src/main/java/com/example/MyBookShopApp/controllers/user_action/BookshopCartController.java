package com.example.MyBookShopApp.controllers.user_action;


import com.example.MyBookShopApp.annotations.UserActionToCartLoggable;
import com.example.MyBookShopApp.annotations.UserActionToPostponedLoggable;
import com.example.MyBookShopApp.data.BalanceTransaction;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.BalanceTransactionRepository;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.MyBookShopApp.data.book.Book2Type.TypeStatus.*;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/books")
public class BookshopCartController {

    private static final String CART_REDIRECT= "redirect:/books/cart";

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    private final BookService bookService;
    private final BalanceTransactionRepository balanceTransactionRepository;

    @Autowired
    public BookshopCartController(BookService bookService, BalanceTransactionRepository balanceTransactionRepository) {
        this.bookService = bookService;
        this.balanceTransactionRepository = balanceTransactionRepository;
    }


    @GetMapping("/cart")
    public String handleCartRequest(
            Model model,
            @AuthenticationPrincipal BookstoreUserDetails user,
            @RequestParam(value = "noMoney", required = false) Boolean noMoney) {
        int cost = 0;
        int oldCost = 0;
        List<Book> books = bookService.getCartBooks(user.getBookstoreUser().getId());
        if (books.isEmpty()) {
            model.addAttribute("isCartEmpty", true);
            model.addAttribute("cost", cost);
        } else {
            if (nonNull(noMoney) && noMoney) {
                model.addAttribute("errorMoney", "К сожалению, у вас не достаточно средств на балансе для данной покупки");
            }
            for (Book booksFromCookieSlug : books) {
                cost = cost + booksFromCookieSlug.discountPrice();
                oldCost = oldCost + booksFromCookieSlug.getPriceOld();
            }
            model.addAttribute("isCartEmpty", false);
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
        return CART_REDIRECT;
    }

    @PostMapping("/changeBookStatus/{slug}")
    @UserActionToCartLoggable
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        if (bookService.isPaid(book, user.getBookstoreUser().getId(), false)) {
            return "redirect:/books/" + slug + "?isPaid=true";
        }
        if (bookService.getCartBooks(user.getBookstoreUser().getId()).contains(book)) {
            return "redirect:/books/" + slug;
        } else {
            bookService.saveBook2User(book, user.getBookstoreUser(), CART);
        }

        return "redirect:/books/" + slug;
    }

    @GetMapping("/pay")
    public String handlePay(@AuthenticationPrincipal BookstoreUserDetails user, Model model) {
        List<Book> books = bookService.getCartBooks(user.getBookstoreUser().getId());
        Double allSum = books.stream().mapToDouble(Book::discountPrice).sum();
        Integer accountMoney = (Integer) ((BindingAwareModelMap) model).get("accountMoney");
        if (accountMoney < allSum) {
            return CART_REDIRECT + "?noMoney=true";
        }
        books.forEach(book -> bookService.saveBook2User(book, user.getBookstoreUser(), PAID));
        String booksName = books.stream().map(book -> book.getTitle() + ", ").collect(Collectors.joining());
        String bookSizeText = books.size() == 1 ? books.size() + " книги: " : books.size() + " книг: ";
        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setUserId(user.getBookstoreUser().getId());
        balanceTransaction.setValue((int) Math.round(allSum) * -1);
        balanceTransaction.setDescription("Покупка " + bookSizeText + booksName);
        balanceTransaction.setTime(new Date());
        balanceTransaction.setTypeStatus(BalanceTransaction.TypeStatus.OK);
        balanceTransactionRepository.save(balanceTransaction);
        return CART_REDIRECT;
    }

}
