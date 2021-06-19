package com.example.MyBookShopApp.data.book;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_review")
public class BookReview implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_review_book"))
    private Book book;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    private Date time;
    @Column(name = "text", length = 1000)
    private String text;
    @Column(name = "rating", columnDefinition = "int default 4")
    private Integer rating;
    @OneToMany(mappedBy = "bookReview", cascade = CascadeType.ALL)
    private List<BookReviewLike> bookReviewLikes = new ArrayList<>();

    public long getLikeCount() {
        return bookReviewLikes.stream().filter(like -> like.getValue()==1).count();
    }

    public long getDisLikeCount() {
        return bookReviewLikes.stream().filter(like -> like.getValue()==-1).count();
    }

}