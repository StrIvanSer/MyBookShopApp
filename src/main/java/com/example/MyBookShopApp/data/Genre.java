package com.example.MyBookShopApp.data;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "genre")
    List<Book> bookListByGenre = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "genre_type_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_genre_genre_type"))
    private GenreType genreType;
}
