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

    @Column(name = "pub_date")
    private Date pubDate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @Column(name = "is_bestseller")
    private Integer isBestseller;

    private String slug;

    private String title;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private String priceOld;

    @Column(name = "discount")
    private String price;


    @ManyToOne
    @JoinColumn(name = "rating_book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_rating_book"))
    private RatingBooks ratingBooks;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_genre"))
    private Genre genre;

}
