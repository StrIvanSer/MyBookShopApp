package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.book.Book;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import springfox.documentation.service.Response;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель поиска для каждой страницы где присутствует поиск
 *
 * @author Иван Стрельцов
 */

@ControllerAdvice
public class BaseMainModelAttribute {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("cartSize")
    public Integer recentBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Response res = (Response) Jsoup
//                .connect("/books")
//                .data("loginField", "login@login.com", "passField", "pass1234")
//                .method(Connection.Method.POST)
//                .execute();
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
