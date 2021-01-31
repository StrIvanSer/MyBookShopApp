package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTTPController {

    @GetMapping("documents/index.html")
    public String getDocumentsPage() {
        return "redirect:/documents";
    }

    @GetMapping("index.html")
    public String getIndex() {
        return "redirect:/";
    }

    @GetMapping("/books/popular.html")
    public String getPopular() {
        return "redirect:/popular";
    }
//
//
    @GetMapping("/authors/index.html")
    public String getAuthors() {
        return "redirect:/authors";
    }

    @GetMapping("/books/recent.html")
    public String getResent() {
        return "redirect:/recent";
    }

}
