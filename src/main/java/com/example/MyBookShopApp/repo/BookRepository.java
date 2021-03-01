package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByPubDateBetween( Date dateFrom, Date dateTo);

    @Query(value = "SELECT * FROM book AS b JOIN rating_book AS rb ON rb.id = b.rating_book_id WHERE rb.rating > 7 " , nativeQuery = true)
    List<Book> getPopularBooks();

    List<Book> findAllByGenre(Genre genre);

    List<Book> findAllByGenre_GenreType(Genre.GenreType genreType);

    @Query("select b from Book as b join b.ratingBooks as r order by r.rating desc")
    Page<Book> findAllByOrderByRatingDesc(Pageable nextPage);

    List<Book> findBooksByAuthorFirstNameContaining(String authorName);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM book WHERE discount = (SELECT MAX(discount) FROM book)", nativeQuery = true)
    List<Book> getBookWithMaxDiscount();

    Page<Book> findBooksByTitleContaining(String bookTitle, Pageable nextPage);

}
