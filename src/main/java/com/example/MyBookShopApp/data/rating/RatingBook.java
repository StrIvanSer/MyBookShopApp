package com.example.MyBookShopApp.data.rating;

import com.example.MyBookShopApp.data.book.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "rating_book")
public class RatingBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_rating_book_book"))
    private Book book;
    @Column(name = "one_star")
    private Integer oneStar;
    @Column(name = "two_star")
    private Integer twoStart;
    @Column(name = "three_star")
    private Integer threeStar;
    @Column(name = "four_star")
    private Integer fourStart;
    @Column(name = "five_star")
    private Integer fiveStar;

}