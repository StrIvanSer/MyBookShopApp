package com.example.MyBookShopApp.data.book;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class BookstoreUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;

    @Column(name = "is_oauth2")
    @ColumnDefault("false")
    private Boolean isOAuth2;
    @Column(name = "id_oauth")
    private String idOAuth;

}
