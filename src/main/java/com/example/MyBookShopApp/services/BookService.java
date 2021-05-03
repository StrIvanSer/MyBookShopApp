package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.annotations.MethodDurationLoggable;
import com.example.MyBookShopApp.data.book.*;
import com.example.MyBookShopApp.data.book.Book2Type.TypeStatus;
import com.example.MyBookShopApp.data.google.api.books.Item;
import com.example.MyBookShopApp.data.google.api.books.Root;
import com.example.MyBookShopApp.errors.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.repo.Book2UserRepository;
import com.example.MyBookShopApp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Сервис для работы с данными класса book
 *
 * @author Иван Стрельцов
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final Book2UserRepository book2UserRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, Book2UserRepository book2UserRepository, RestTemplate restTemplate) {
        this.bookRepository = bookRepository;
        this.book2UserRepository = book2UserRepository;
        this.restTemplate = restTemplate;
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

    @MethodDurationLoggable(className = "BookService" , timeThreshold = 2000)
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

    @MethodDurationLoggable(className = "BookService")
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

    public void removeFromBook2User(Book book, BookstoreUser user) {
        Book2User book2User = book2UserRepository.findByUserIdAndBookId(user.getId(), book.getId());
        if (nonNull(book2User)) {
            book2UserRepository.delete(book2User);
        }
    }

    @MethodDurationLoggable(className = "BookService" , timeThreshold = 1500)
    public List<Book> getCartBooks(Integer id) {
        return bookRepository.getCartBooks(id);
    }

    @MethodDurationLoggable(className = "BookService" , timeThreshold = 1200)
    public void saveBook2User(Book book, BookstoreUser user, TypeStatus typeStatus) {
        Book2User book2User = book2UserRepository.findByUserIdAndBookId(user.getId(), book.getId());
        if (nonNull(book2User) && !book2User.getBook2Type().getTypeStatus().equals(typeStatus)) {
            book2User.getBook2Type().setTypeStatus(typeStatus);
            book2UserRepository.save(book2User);
        } else {
            Book2Type book2Type = new Book2Type();
            Book2User newBook2User = new Book2User();
            book2Type.setTypeStatus(typeStatus);
            newBook2User.setBook(book);
            newBook2User.setUser(user);
            newBook2User.setBook2Type(book2Type);
            book2UserRepository.save(newBook2User);
        }
    }

    @MethodDurationLoggable(className = "BookService" , timeThreshold = 500)
    public List<Book> getPostponedBooks(Integer id) {
        return bookRepository.getPostponedBooks(id);
    }

    public List<Book> findBooksBySlugIn(String[] cookieSlugs) {
        return bookRepository.findBooksBySlugIn(cookieSlugs);
    }

    @Value("${google.books.api.key}")
    private String apiKey;


    public List<Book> getPageOfGoogleBooksSearchResult(String searchWord, Integer offset, Integer limit){
        String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes" +
                "?q=" + searchWord +
                "&key=" + apiKey +
                "&filter=paid-ebooks" +
                "&startIndex=" + offset +
                "&maxResults=" + limit;

        Root root = restTemplate.getForEntity(REQUEST_URL, Root.class).getBody();
        ArrayList<Book> list = new ArrayList<>();
        if(root != null){
            for (Item item : root.getItems()){
                Book book = new Book();
                if(item.getVolumeInfo() != null){
                    book.setAuthor(new Author(item.getVolumeInfo().getAuthors()));
                    book.setTitle(item.getVolumeInfo().getTitle());
                    book.setImage(item.getVolumeInfo().getImageLinks().getThumbnail());
                }
                if (item.getSaleInfo()!=null){
                    book.setPrice(item.getSaleInfo().getRetailPrice().getAmount());
                    Double oldPrice = item.getSaleInfo().getListPrice().getAmount();
                    book.setPriceOld(oldPrice.intValue());
                }
                list.add(book);
            }
        }
        return list;
    }

}
