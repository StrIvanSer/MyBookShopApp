package com.example.MyBookShopApp.data.book;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "book2user")
public class Book2User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BookstoreUser user;

    @OneToOne
    @JoinColumn(name = "book_type_id", referencedColumnName = "id")
    private Book2Type book2Type;

}
