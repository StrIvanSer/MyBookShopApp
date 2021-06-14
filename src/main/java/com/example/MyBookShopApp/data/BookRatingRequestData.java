package com.example.MyBookShopApp.data;

import lombok.Data;

@Data
public class BookRatingRequestData {

    private String bookId;
    private Integer value;

}