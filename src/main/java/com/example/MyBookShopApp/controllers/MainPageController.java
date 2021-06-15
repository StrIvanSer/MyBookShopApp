package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.book.Tag;
import com.example.MyBookShopApp.errors.EmptySearchException;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;


/**
 * Главная страница
 *
 * @author Иван Стрельцов
 */
@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;

    private static final Calendar calendar = Calendar.getInstance();

    static {
        calendar.add(Calendar.MONTH, -6);
    }

    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(@AuthenticationPrincipal BookstoreUserDetails user) {
        if (isNull(user)) return bookService.getPageOfPopularBooks(0, 6).getContent();
        return bookService.getPageOfPopularBooksWithActiveUser(0, 6, user.getBookstoreUser().getId()).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getPageOfRecentBooksData(calendar.getTime(), new Date(), 0, 6).getContent();
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecentBooksData(calendar.getTime(), new Date(), offset, limit).getContent());
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks(@AuthenticationPrincipal BookstoreUserDetails user) {
        if (isNull(user)) return bookService.getPageOfPopularBooks(0, 6).getContent();
        return bookService.getPageOfRecommendedBooks(0, 6, user.getBookstoreUser().getId()).getContent();
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit,
                                                @AuthenticationPrincipal BookstoreUserDetails user) {
        if(isNull(user)) return new BooksPageDto(bookService.getPageOfPopularBooks(offset, limit).getContent());
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit, user.getBookstoreUser().getId()).getContent());
    }

    @GetMapping(value = {"/search/", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) throws EmptySearchException {
        if (searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults",
                    bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5));
            return "/search/index";
        } else {
            throw new EmptySearchException("Поиск по null невозможен");
        }
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }

    @ModelAttribute("tags")
    public List<Tag> tags() {
        return tagService.getAll();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/help")
    public String helpPage() {
        return "faq";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "contacts";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "/search/index";
    }

    //GOOGLE API
//    @GetMapping(value = {"/search/", "/search/{searchWord}"})
//    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
//                                   Model model) throws EmptySearchException {
//        if (searchWordDto != null) {
//            model.addAttribute("searchWordDto", searchWordDto);
//            model.addAttribute("searchResults",
//                    bookService.getPageOfGoogleBooksSearchResult(searchWordDto.getExample(), 0, 5));
//            return "/search/index";
//        } else {
//            throw new EmptySearchException("Поиск по null невозможен");
//        }
//    }
//
//    @GetMapping("/search/page/{searchWord}")
//    @ResponseBody
//    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
//                                          @RequestParam("limit") Integer limit,
//                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
//        return new BooksPageDto(bookService.getPageOfGoogleBooksSearchResult(searchWordDto.getExample(), offset, limit));
//    }

}
