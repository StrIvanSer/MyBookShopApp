package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTTPController {

    @GetMapping("my.html")
    public String getDocumentsPage() {
        return "redirect:/my";
    }

    @GetMapping("documents/slug.html")
    public String getDocumentsSlugPage() {
        return "redirect:/documents/slug";
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

    @GetMapping("/500")
    public String get500() {
        return "redirect:/recent";
    }

    @GetMapping("http://127.0.0.1:8085/accept/payment/")
    public String getAcceptPayment() {
        return "redirect:/accept/payment/";
    }

}
