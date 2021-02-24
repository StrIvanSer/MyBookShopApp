package com.example.MyBookShopApp.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "book")
public class Book implements Serializable {

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

    @ManyToOne
    @JoinColumn(name = "rating_book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_rating_book"))
    private RatingBooks ratingBooks;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_genre"))
    private Genre genre;

}
