package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Genre;
import com.example.MyBookShopApp.data.GenreType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByReleaseBetween( Date dateFrom, Date dateTo);

    @Query(value = "SELECT * FROM book AS b JOIN rating_book AS rb ON rb.id = b.rating_book_id WHERE rb.rating > 7 " , nativeQuery = true)
    List<Book> getPopularBooks();

    List<Book> findAllByGenre(Genre genre);

    List<Book> findAllByGenre_GenreType(GenreType genreType);

    @Query("select b from Book as b join b.ratingBooks as r order by r.rating desc")
    Page<Book> findAllByOrderByRatingDesc(Pageable nextPage);

}
