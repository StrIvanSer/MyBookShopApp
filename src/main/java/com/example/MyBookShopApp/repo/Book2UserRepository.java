package com.example.MyBookShopApp.repo;

import java.util.List;
import com.example.MyBookShopApp.data.book.Book2Type;
import com.example.MyBookShopApp.data.book.Book2User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2UserRepository extends JpaRepository<Book2User, Integer> {

    Book2User findByUserIdAndBookId(Integer userId, Integer id);

    Book2User findByUserIdAndBookIdAndBook2Type_TypeStatusIn(Integer userId, Integer id, List<Book2Type.TypeStatus> typeStatus);

//    UserTemp findById(Integer Id);

}

