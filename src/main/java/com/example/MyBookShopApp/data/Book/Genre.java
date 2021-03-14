package com.example.MyBookShopApp.data.Book;


import com.example.MyBookShopApp.data.Book.Book;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "genre")
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "genre")
    List<Book> bookListByGenre = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "genre_type")
    private GenreType genreType;

    @AllArgsConstructor
    @Getter
    public enum GenreType {

        EASY_READING("Легкое чтение"),
        SERIOUS_READING("Серьёзное чтение"),
        BUSINESS_LITERATURE("Деловая литература"),
        DRAMA("Драматургия");

        public final String genreTypeName;

        @Override
        public String toString() {
            return genreTypeName;
        }
    }

}