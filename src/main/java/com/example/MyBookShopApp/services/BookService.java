package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooksData() {
        List<Book> books = jdbcTemplate.query(
                "SELECT b.id, concat_ws(' ', a.firstName, a.lastName) author , b.title, b.priceOld, b.price" +
                        " FROM books b " +
                        "JOIN authors a on a.id = b.author_id",
                (ResultSet rs, int rowNum) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setAuthor(rs.getString("author"));
                    book.setTitle(rs.getString("title"));
                    book.setPriceOld(rs.getString("priceOld"));
                    book.setPriceNew(rs.getString("price"));
                    return book;
                }
        );
        return new ArrayList<>(books);
    }

}
