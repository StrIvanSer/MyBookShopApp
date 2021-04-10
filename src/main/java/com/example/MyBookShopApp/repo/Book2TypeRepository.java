package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Book2Type;
import com.example.MyBookShopApp.data.book.Book2User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2TypeRepository extends JpaRepository<Book2Type, Integer> {

//    UserTemp findById(Integer Id);

}

