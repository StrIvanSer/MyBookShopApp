package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Book.Book;
import com.example.MyBookShopApp.data.Book.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findPageOfBooksByPubDateBetweenOrderByPubDate(Date dateFrom, Date dateTo, Pageable nextPage);

    @Query(value = "SELECT * FROM book AS b JOIN rating_book AS rb ON rb.id = b.rating_book_id WHERE rb.rating > 7 ", nativeQuery = true)
    Page<Book> getPageOfPopularBooks(Pageable nextPage);

    Page<Book> findAllByGenre(Genre genre, Pageable nextPage);

    Page<Book> findAllByGenre_GenreType(Genre.GenreType genreType, Pageable nextPage);

    @Query("SELECT b FROM Book AS b JOIN b.ratingBooks AS r WHERE r.rating > 7 ORDER BY r.rating DESC")
    Page<Book> findAllByOrderByRatingDesc(Pageable nextPage);

    List<Book> findBooksByAuthorFirstNameContaining(String authorName);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("FROM Book WHERE isBestseller = 1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM book WHERE discount = (SELECT MAX(discount) FROM book)", nativeQuery = true)
    List<Book> getBookWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    Page<Book> findAllByGenreId(Integer genreId, Pageable nextPage);

    Page<Book> findByAuthorId(Integer authorId, Pageable nextPage);

    @Query("SELECT b FROM Book AS b JOIN b.tagList AS t WHERE t.id = ?1")
    Page<Book> findBooksByTag(Integer tagId, Pageable nextPage);

    Book findBookBySlug(String slug);

    List<Book> findBooksByTitleContaining(String bookTitle);
}

