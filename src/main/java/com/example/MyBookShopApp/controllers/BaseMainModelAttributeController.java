package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.book.Book;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель поиска для каждой страницы где присутствует поиск
 *
 * @author Иван Стрельцов
 */

@ControllerAdvice
public class BaseMainModelAttributeController {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("cartSize")
    public Integer recentBooks(@CookieValue(value = "cartContents", required = false) String cartContents) {
        return 0;
//        Cookie[] cookies = Cookie;
//        if (cartContents == null || cartContents.equals("")) {
//            return 0;
//        } else {
//            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
//            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
//            String[] cookieSlugs = cartContents.split("/");
//            return cookieSlugs.length;
////            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
//        }
    }

}
