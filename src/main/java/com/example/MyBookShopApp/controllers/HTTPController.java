package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTTPController {

    @GetMapping("/")
    public String getMainPage() {
        return "redirect:/bookshop/main";
    }

    @GetMapping("index.html")
    public String getIndex() {
        return "redirect:/bookshop/main";
    }

    @GetMapping("/genres/index.html")
    public String getGenres() {
        return "redirect:/bookshop/genres";
    }


    @GetMapping("/authors/index.html")
    public String getAuthors() {
        return "redirect:/authors";
    }

}
