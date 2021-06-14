package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findPageOfBooksByPubDateBetweenOrderByPubDate(Date dateFrom, Date dateTo, Pageable nextPage);

    @Query(value = "SELECT b.* " +
            "FROM book AS b " +
            "JOIN rating_book AS rb ON rb.book_id = b.id " +
            "LEFT JOIN book2user AS bu ON bu.book_id = b.id " +
            "LEFT JOIN book2user_type AS but ON but.id = bu.book_type_id " +
            "WHERE (type IS NULL OR type = 0) " +
            "GROUP BY b.id, bu.book_id, rb.five_star " +
            "ORDER BY rb.five_star DESC , COUNT(bu.book_id) DESC  ", nativeQuery = true)
    Page<Book> getPageOfPopularBooks(Pageable nextPage);

    @Query(value = "SELECT b.* " +
            "FROM recently_viewed AS rv " +
            "RIGHT JOIN book AS b ON b.id = rv.book_id AND rv.last_veiw_date_time > ?2 " +
            "JOIN rating_book AS rb ON rb.book_id = b.id " +
            "WHERE (rv.user_id = ?1 OR rv.user_id IS NULL) " +
            "    AND b.id NOT IN (SELECT bu.book_id " +
                            "       FROM book2user AS bu " +
                            "       JOIN book2user_type AS but ON but.id = bu.book_type_id AND but.type IN (2,3) " +
                            "        AND bu.user_id = ?1) " +
            "GROUP BY  b.id, rv.last_veiw_date_time, rb.five_star " +
            "ORDER BY rv.last_veiw_date_time DESC NULLS LAST, rb.five_star DESC", nativeQuery = true)
    Page<Book> getPageOfRecommendBooks(Integer userId, Timestamp limitDateTime, Pageable nextPage);

    @Query(value = "SELECT b.* " +
            "FROM book AS b " +
            "JOIN recently_viewed AS rv ON rv.book_id = b.id " +
            "WHERE rv.user_id = ?2 AND rv.last_veiw_date_time >= ?1 " +
            "ORDER BY rv.last_veiw_date_time DESC ", nativeQuery = true)
    Page<Book> getPageOfRecentlyViewed(Timestamp limitDateTime, Integer userId, Pageable nextPage);

    Page<Book> findAllByGenre(Genre genre, Pageable nextPage);

    Page<Book> findAllByGenre_GenreType(Genre.GenreType genreType, Pageable nextPage);

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

    List<Book> findBooksBySlugIn(String[] cookieSlugs);

    Book findBookById(Integer id);


    @Query(value = "SELECT *" +
            " FROM book AS b " +
            " JOIN book2user AS bu ON bu.book_id = b.id" +
            " JOIN book2user_type AS but ON but.id = bu.book_type_id" +
            " WHERE but.type = 0 AND bu.user_id = ?1", nativeQuery = true)
    List<Book> getPostponedBooks(Integer idUser);

    @Query(value = "SELECT *" +
            " FROM book AS b " +
            " JOIN book2user AS bu ON bu.book_id = b.id" +
            " JOIN book2user_type AS but ON but.id = bu.book_type_id" +
            " WHERE but.type = 1 AND bu.user_id = ?1", nativeQuery = true)
    List<Book> getCartBooks(Integer idUser);

    @Query(value = "SELECT *" +
            " FROM book AS b " +
            " JOIN book2user AS bu ON bu.book_id = b.id" +
            " JOIN book2user_type AS but ON but.id = bu.book_type_id" +
            " WHERE but.type = 2 AND bu.user_id = ?1", nativeQuery = true)
    List<Book> getPaidBooks(Integer idUser);
}

