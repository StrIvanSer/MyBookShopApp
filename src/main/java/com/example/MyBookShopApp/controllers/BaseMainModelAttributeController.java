package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book.Book;
import com.example.MyBookShopApp.data.SearchWordDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель поиска для каждой страницы где присутствует поиск
 *
 * @author Иван Стрельцов
 */
@Setter
@Getter
public abstract class BaseMainModelAttributeController {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

}
