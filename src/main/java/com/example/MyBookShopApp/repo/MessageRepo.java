package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Integer> {

}
