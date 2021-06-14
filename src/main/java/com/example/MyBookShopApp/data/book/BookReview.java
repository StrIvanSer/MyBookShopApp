package com.example.MyBookShopApp.data.book;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_review")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_review_book"))
    private Book book;
    @Column(name = "user_id")
    private Integer userId;
    //Временно до появления авторизации
    @Column(name = "user_name")
    private String userName;
    private Date time;
    @Column(name = "text", length = 1000)
    private String text;
    @Column(name = "rating", columnDefinition = "int default 4")
    private Integer rating;

}
