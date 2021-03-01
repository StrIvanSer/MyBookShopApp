package com.example.MyBookShopApp.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
@ApiModel(description = "entity representing a book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id generation by db")
    private Integer id;

    @Column(name = "pub_date")
    @ApiModelProperty("date pub")
    private Date pubDate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    private Author author;

    @Column(name = "is_bestseller")
    @ApiModelProperty("if 1 then Bestseller")
    private Integer isBestseller;

    @ApiModelProperty("mnemonical identity sequence of charecters")
    private String slug;

    @ApiModelProperty("book title")
    private String title;

    @ApiModelProperty("mnemonical identity sequence of charecters")
    private String image;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty("description")
    private String description;

    @Column(name = "price")
    @ApiModelProperty("price old")
    private Integer priceOld;

    @ApiModelProperty("discount")
    @Column(name = "discount")
    private String price;


    @ManyToOne
    @JoinColumn(name = "rating_book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_rating_book"))
    @JsonIgnore
    private RatingBooks ratingBooks;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_genre"))
    @JsonIgnore
    private Genre genre;

}
