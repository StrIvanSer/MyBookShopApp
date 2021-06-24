package com.example.MyBookShopApp.data.rating;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RatingCount {

    private Integer total;
    private Integer average;

    public RatingCount(Integer total, Integer average) {
        this.total = total;
        this.average = average;
    }
}
