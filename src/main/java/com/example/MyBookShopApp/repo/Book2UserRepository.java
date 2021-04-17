package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Book2User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2UserRepository extends JpaRepository<Book2User, Integer> {

    Book2User findByUserIdAndBookId(Integer userId, Integer id);

//    UserTemp findById(Integer Id);

}

