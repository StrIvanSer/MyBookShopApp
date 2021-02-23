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
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany
    private List<Book> bookList = new ArrayList<>();

    @Override
    public String toString() {
        return firstName  + " " + lastName;
    }
}
