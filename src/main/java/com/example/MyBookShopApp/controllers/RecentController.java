package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

import static com.example.MyBookShopApp.Util.StringToDateFormatter.formatToDate;


/**
 * Раздел новинки
 *
 * @author Иван Стрельцов
 */
@Controller
public class RecentController {

    private final BookService bookService;

    @Autowired
    public RecentController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/recent")
    public String getRecent(Model model) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        model.addAttribute("recentBooks", bookService.getPageOfRecentBooksData(calendar.getTime(), new Date(), 0, 5));
        model.addAttribute("dateFrom", calendar.getTime());
        model.addAttribute("dateTo", new Date());
        return "/books/recent";
    }

    @GetMapping("/books/recent/page")
    @ResponseBody
    public BooksPageDto getNextSearchPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "from", defaultValue = "") String from,
            @RequestParam(value = "to", defaultValue = "") String to) {
        return new BooksPageDto(bookService.getPageOfRecentBooksData(formatToDate(from),
                formatToDate(to), offset, limit).getContent());
    }

}
