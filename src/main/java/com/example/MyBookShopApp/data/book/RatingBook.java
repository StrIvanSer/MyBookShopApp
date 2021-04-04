package com.example.MyBookShopApp.data.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;
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
    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
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

    public Integer getSizeStars() {
        return oneStar + twoStart + threeStar + fourStart + fiveStar;
    }

    public Integer getAvgStar() {
        return ((fiveStar * 5 + fourStart * 4 + threeStar * 3 + twoStart * 2 + oneStar) /
                (oneStar + twoStart + threeStar + fourStart + fiveStar));
    }

}