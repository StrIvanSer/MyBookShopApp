package com.example.MyBookShopApp.data.tag;


import com.example.MyBookShopApp.data.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Tag")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "book2tag",
            joinColumns = @JoinColumn(name = "tag_id"), foreignKey = @ForeignKey(name = "fk_tag_book"),
            inverseJoinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book_tag"))
    )
    private  List<Book> bookList;

    private String name;

}
