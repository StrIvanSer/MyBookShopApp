package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookstoreUserRepository extends JpaRepository<BookstoreUser, Integer> {

    BookstoreUser findBookstoreUserByEmail(String email);
    BookstoreUser findBookstoreUserByPhone(String phone);

}
