package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
