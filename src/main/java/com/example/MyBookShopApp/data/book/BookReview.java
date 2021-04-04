package com.example.MyBookShopApp.data.book;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
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
