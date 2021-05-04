package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Book2Type;
import com.example.MyBookShopApp.data.book.Book2User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2UserRepository extends JpaRepository<Book2User, Integer> {

    Book2User findByUserIdAndBookId(Integer userId, Integer id);

    Book2User findByUserIdAndBookIdAndBook2Type_TypeStatus(Integer userId, Integer id, Book2Type.TypeStatus typeStatus);

//    UserTemp findById(Integer Id);

}

