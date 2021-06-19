package com.example.MyBookShopApp.data.book;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book2user_type")
public class Book2Type implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private TypeStatus typeStatus;

    @OneToOne(mappedBy = "book2Type")
    private Book2User book2User;

    @AllArgsConstructor
    @Getter
    public enum TypeStatus {

        KEPT("Отложена"),
        CART("В корзине"),
        PAID("Куплена"),
        ARCHIVED("В архиве");

        public final String TypeName;

        @Override
        public String toString() {
            return TypeName;
        }
    }

}
