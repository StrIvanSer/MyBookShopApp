package com.example.MyBookShopApp.data.book;

import com.example.MyBookShopApp.data.file.BookFile;
import com.example.MyBookShopApp.data.tag.Tag;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "book")
@ApiModel(description = "entity representing a book")
public class Book extends RepresentationModel<Book> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id generation by db")
    private Integer id;

    @Column(name = "pub_date")
    @ApiModelProperty("date pub")
    private Date pubDate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_author"))
    @JsonIgnore
    private Author author;

    @JsonGetter("authors")
    public String authorsFullName() {
        return author.toString();
    }

    @Column(name = "is_bestseller")
    @ApiModelProperty("if 1 then Bestseller")
    private Integer isBestseller;

    @ApiModelProperty("mnemonical identity sequence of characters")
    private String slug;

    @ApiModelProperty("book title")
    private String title;

    @ApiModelProperty("mnemonical identity sequence of charecters")
    private String image;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty("description")
    private String description;

    @Column(name = "price")
    @JsonProperty("price")
    @ApiModelProperty("price old")
    private Integer priceOld;

    @Column(name = "discount")
    @JsonProperty("discount")
    @ApiModelProperty("discount value for book")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_genre"))
    @JsonIgnore
    private Genre genre;

    @ManyToMany(mappedBy = "bookList", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Tag> tagList = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<BookFile> bookFileList = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<BookReview> bookReviewList = new ArrayList<>();

    @JsonProperty
    public Integer discountPrice() {
        return priceOld - Math.toIntExact(Math.round(price * priceOld));
    }

}
