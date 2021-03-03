package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagController extends BaseMainModelAttributeController{

    private final TagService tagService;
    private final BookService bookService;

    @Autowired
    public TagController(TagService tagService, BookService bookService) {
        this.tagService = tagService;
        this.bookService = bookService;
    }

    @GetMapping("/tags/{tagId:\\d+}")
    public String getTag(@PathVariable Integer tagId, Model model) {
        model.addAttribute("tagBooks", bookService.getBooksByTag(tagId, 0, 5));
        model.addAttribute("tag", tagService.getTag(tagId));
        return "tags/index";
    }

    @GetMapping("/books/tag/{tagId:\\d+}")
    @ResponseBody
    public BooksPageDto getNextPage(
            @PathVariable Integer tagId,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) {
        return new BooksPageDto(bookService.getBooksByTag(tagId, offset, limit).getContent());
    }

}
