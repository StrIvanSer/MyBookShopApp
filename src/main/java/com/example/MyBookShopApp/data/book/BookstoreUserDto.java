package com.example.MyBookShopApp.data.book;

import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookstoreUserDto {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String passwordConfirm;

    public BookstoreUserDto(BookstoreUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
