package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Book2User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2UserRepository extends JpaRepository<Book2User, Integer> {

//    UserTemp findById(Integer Id);

}

