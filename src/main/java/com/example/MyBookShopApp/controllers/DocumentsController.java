package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentsController extends BaseMainModelAttributeController {

    @GetMapping("/documents")
    public String documentPage(){
        return "/documents/index";
    }

    @GetMapping("/documents/slug")
    public String documentsSlugPage(){
        return "/documents/slug";
    }


}
