package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.book.BookstoreUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Таблица роли пользователей
 * @author Streltsov Ivan
 * @version  1.0
 */

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<BookstoreUser> users;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

}
