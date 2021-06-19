package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByFirstName(String name);
    Author getById(Integer id);

}
