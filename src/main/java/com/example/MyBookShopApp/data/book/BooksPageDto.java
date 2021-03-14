package com.example.MyBookShopApp.data.book;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BooksPageDto {

    private Integer count;
    private List<Book> books;

    public BooksPageDto(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }

}
