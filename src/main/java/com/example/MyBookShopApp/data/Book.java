package com.example.MyBookShopApp.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    private String title;
    private String priceOld;
    private String price;
    private Date release;

    @OneToOne
    @JoinColumn(name = "rating_books_id", referencedColumnName = "id")
    private RatingBooks ratingBooks;

}
