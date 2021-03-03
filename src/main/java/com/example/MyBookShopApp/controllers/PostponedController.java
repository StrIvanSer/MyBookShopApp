package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostponedController extends BaseMainModelAttributeController {

    @GetMapping("/postponed")
    public String authorsPage(){
        return "/postponed";
    }

}
