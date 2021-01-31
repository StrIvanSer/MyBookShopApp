package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Book> getRecentBooksData(Date dateFrom, Date dateTo) {
        List<Book> books =
                jdbcTemplate.query(
                        "SELECT b.id, concat_ws(' ', a.firstName, a.lastName) author , b.title, b.priceOld, b.price, b.release " +
                                "FROM books b " +
                                "JOIN authors a ON a.id = b.author_id " +
                                "WHERE b.release > ? AND b.release < ? ",
                        (ResultSet rs, int rowNum) -> {
                            Book book = new Book();
                            book.setId(rs.getInt("id"));
                            book.setAuthor(rs.getString("author"));
                            book.setTitle(rs.getString("title"));
                            book.setPriceOld(rs.getString("priceOld"));
                            book.setPriceNew(rs.getString("price"));
                            book.setRelease(rs.getTimestamp("release"));
                            return book;
                        },
                        dateFrom, dateTo);
        return new ArrayList<>(books);
    }

    public List<Book> getPopularBooks() {
        List<Book> books =
                jdbcTemplate.query(
                        "SELECT b.id, concat_ws(' ', a.firstName, a.lastName) author , b.title, b.priceOld, b.price " +
                                "FROM books b " +
                                "JOIN authors a ON a.id = b.author_id " +
                                "JOIN rating_books rb ON a.id = rb.books_id " +
                                "WHERE rb.rat > 6 ",
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
