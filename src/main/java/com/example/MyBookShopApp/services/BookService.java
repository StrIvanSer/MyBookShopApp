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

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * Сервис для работы с данными класса book
 *
 * @author Иван Стрельцов
 */
@Service
public class BookService {

    // Лимит 7 дней для недавно просмотренных книг
    public static Timestamp LIMIT_DATE_TIME_RECENTLY_VIEWED = Timestamp.valueOf(now().minusMinutes(60L * 24L * 7L));

    // Лимит 1 день для недавно просмотренных книг для отображения в рекомендованных книгах
    public static Timestamp LIMIT_DATE_TIME_RECENTLY_RECOMMEND = Timestamp.valueOf(now().minusMinutes(60L * 24L));

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

    /***
     * Метод для получения рекомендованных книг, основан на недавно просмотренных книгах(в течении 24 часов) пользователем
     * и на количестве оценок из 5*, исключены книги со статусом "Куплена" и "В архиве"
     *
     * @param limit лимит
     * @param userId id пользователя
     * @return список рекомендованных книг
     */
    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit, Integer userId) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getPageOfRecommendBooks(userId, LIMIT_DATE_TIME_RECENTLY_RECOMMEND, nextPage);
    }

    /***
     * Метод для получения популярных книг, основан на количестве оценок из 5* и количества отложенных книг всеми
     * пользователями.
     *
     * @param page страница
     * @param limit лимит
     * @return Список популярных книг
     */
    @MethodDurationLoggable(className = "BookService", timeThreshold = 2000)
    public Page<Book> getPageOfPopularBooks(Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.getPageOfPopularBooks(nextPage);
    }

    /***
     * Метод для получения популярных книг, основан на количестве оценок из 5*, недавно просмотренных книг текущем пользователем
     * и количества отложенных книг всеми пользователями.
     *
     * @param page страница
     * @param limit лимит
     * @return Список популярных книг
     */
    @MethodDurationLoggable(className = "BookService", timeThreshold = 2000)
    public Page<Book> getPageOfPopularBooksWithActiveUser(Integer page, Integer limit, Integer userId) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.getPageOfPopularBooksWithActiveUser(userId, LIMIT_DATE_TIME_RECENTLY_RECOMMEND, nextPage);
    }

    public Page<Book> getPageOfRecentlyViewedBooks(Integer offset, Integer limit, Integer userId) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getPageOfRecentlyViewed(LIMIT_DATE_TIME_RECENTLY_VIEWED, userId, nextPage);
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

    @MethodDurationLoggable(className = "BookService", timeThreshold = 1500)
    public List<Book> getCartBooks(Integer id) {
        return bookRepository.getCartBooks(id);
    }

    public List<Book> getPaidBooks(Integer id) {
        return bookRepository.getPaidBooks(id);
    }

    public List<Book> getArchiveBooks(Integer id) {
        return bookRepository.getArchiveBooks(id);
    }

    @MethodDurationLoggable(className = "BookService", timeThreshold = 1200)
    public void saveBook2User(Book book, BookstoreUser user, TypeStatus typeStatus) {
        Book2User book2User = book2UserRepository.findByUserIdAndBookId(user.getId(), book.getId());

        if (nonNull(book2User) && !book2User.getBook2Type().getTypeStatus().equals(typeStatus) &&
                !book2User.getBook2Type().getTypeStatus().equals(TypeStatus.PAID)
                && !book2User.getBook2Type().getTypeStatus().equals(TypeStatus.ARCHIVED)) {
            book2User.getBook2Type().setTypeStatus(typeStatus);
            book2UserRepository.save(book2User);
        } else if (nonNull(book2User) && book2User.getBook2Type().getTypeStatus().equals(TypeStatus.PAID)) {
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

    @MethodDurationLoggable(className = "BookService", timeThreshold = 500)
    public List<Book> getPostponedBooks(Integer id) {
        return bookRepository.getPostponedBooks(id);
    }

    @Value("${google.books.api.key}")
    private String apiKey;


    public List<Book> getPageOfGoogleBooksSearchResult(String searchWord, Integer offset, Integer limit) {
        String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes" +
                "?q=" + searchWord +
                "&key=" + apiKey +
                "&filter=paid-ebooks" +
                "&startIndex=" + offset +
                "&maxResults=" + limit;

        Root root = restTemplate.getForEntity(REQUEST_URL, Root.class).getBody();
        ArrayList<Book> list = new ArrayList<>();
        if (root != null) {
            for (Item item : root.getItems()) {
                Book book = new Book();
                if (item.getVolumeInfo() != null) {
                    book.setAuthor(new Author(item.getVolumeInfo().getAuthors()));
                    book.setTitle(item.getVolumeInfo().getTitle());
                    book.setImage(item.getVolumeInfo().getImageLinks().getThumbnail());
                }
                if (item.getSaleInfo() != null) {
                    book.setPrice(item.getSaleInfo().getRetailPrice().getAmount());
                    double oldPrice = item.getSaleInfo().getListPrice().getAmount();
                    book.setPriceOld((int) oldPrice);
                }
                list.add(book);
            }
        }
        return list;
    }

    public boolean isPaid(Book book, Integer id, Boolean checkArchive) {
        Book2User book2User = book2UserRepository.findByUserIdAndBookIdAndBook2Type_TypeStatusIn(id, book.getId(), asList(TypeStatus.PAID, TypeStatus.ARCHIVED));
        if (checkArchive && nonNull(book2User)) {
            return !book2User.getBook2Type().getTypeStatus().equals(TypeStatus.ARCHIVED);
        }
        return nonNull(book2User);
    }

    @Transactional(REQUIRES_NEW)
    public void removeBook(Integer id) {
        bookRepository.deleteCascadeUserToBookById(id);
        bookRepository.deleteCascadeRatingBookById(id);
        bookRepository.deleteCascadeRecentlyBookById(id);
        bookRepository.deleteById(id);
    }
}
