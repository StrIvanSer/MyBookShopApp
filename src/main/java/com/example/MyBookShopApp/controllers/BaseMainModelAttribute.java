package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Модель поиска для каждой страницы где присутствует поиск
 *
 * @author Иван Стрельцов
 */

@ControllerAdvice
public class BaseMainModelAttribute {
    @Autowired
    BookService bookService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("cartSize")
    public Integer getCartSize(@AuthenticationPrincipal BookstoreUserDetails user){
        if(nonNull(user)){
            return bookService.getCartBooks(user.getBookstoreUser().getId()).size();
        }
        return 0;
    }

    @ModelAttribute("postponedSize")
    public Integer getPostponedSize(@AuthenticationPrincipal BookstoreUserDetails user){
        if(nonNull(user)){
            return bookService.getPostponedBooks(user.getBookstoreUser().getId()).size();
        }
        return 0;
    }
}
