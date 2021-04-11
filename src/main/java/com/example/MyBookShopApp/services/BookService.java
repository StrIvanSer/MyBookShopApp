package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.Book2Type;
import com.example.MyBookShopApp.data.book.Book2User;
import com.example.MyBookShopApp.data.book.Genre;
import com.example.MyBookShopApp.errors.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.repo.Book2TypeRepository;
import com.example.MyBookShopApp.repo.Book2UserRepository;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.repo.BookstoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.example.MyBookShopApp.data.book.Book2Type.TypeStatus.KEPT;
import static java.util.Objects.isNull;

/**
 * Сервис для работы с данными класса book
 *
 * @author Иван Стрельцов
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookstoreUserRepository bookstoreUserRepository;
    private final Book2UserRepository book2UserRepository;
    private final Book2TypeRepository book2TypeRepository;


    @Autowired
    public BookService(BookRepository bookRepository, BookstoreUserRepository bookstoreUserRepository,
                       Book2UserRepository book2UserRepository, Book2TypeRepository book2TypeRepository) {
        this.bookRepository = bookRepository;
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.book2UserRepository = book2UserRepository;
        this.book2TypeRepository = book2TypeRepository;
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public Page<Book> getBooksByAuthorId(Integer authorId, Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.findByAuthorId(authorId, nextPage);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price) {
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPriceDiscount() {
        return bookRepository.getBookWithMaxDiscount();
    }

    public List<Book> getBestsellers() {
        return bookRepository.getBestsellers();
    }

    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    public Page<Book> getPageOfRecentBooksData(Date dateFrom, Date dateTo, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findPageOfBooksByPubDateBetweenOrderByPubDate(dateFrom, dateTo, nextPage);
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfPopularBooks(Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.getPageOfPopularBooks(nextPage);
    }

    public Page<Book> getPageBookByGenreType(Genre.GenreType genreType, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByGenre_GenreType(genreType, nextPage);
    }

    public Page<Book> getPageBookByGenre(Genre genre, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByGenre(genre, nextPage);
    }

    public Page<Book> getPageBookByGenreId(Integer genreId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByGenreId(genreId, nextPage);
    }

    public Page<Book> getBooksByTag(Integer tagId, Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.findBooksByTag(tagId, nextPage);
    }

    public List<Book> getBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if (isNull(title) || title.equals("") || title.length() < 1) {
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        } else {
            List<Book> data = bookRepository.findBooksByTitleContaining(title);
            if (data.size() > 0) {
                return data;
            } else {
                throw new BookstoreApiWrongParameterException("No data found with specified parameters...");
            }
        }
    }

    public Book getBookById(Integer id) {
        return bookRepository.findBookById(id);
    }

    public Book findBookBySlug(String slug) {
        return bookRepository.findBookBySlug(slug);
    }

    public void save(Book bookToUpdate) {
        bookRepository.save(bookToUpdate);
    }

    public void appendToPostpone(Book book) {
        Book2Type book2Type = new Book2Type();
        book2Type.setType(KEPT);
        book2TypeRepository.save(book2Type);
        Book2User book2User = new Book2User();
        book2User.setBook(book);
        book2User.setUser(bookstoreUserRepository.findById(1).get());
        book2User.setBook2Type(book2Type);
        book2UserRepository.save(book2User);
    }

    public void removeFromPostpone(Book book) {
        Book2User book2User = book.getBook2Users().stream().filter(user -> user.getUser().getId().equals(1)).findFirst().get();
        book2UserRepository.delete(book2User);
        book2TypeRepository.delete(book2User.getBook2Type());
    }
}
