package com.example.MyBookShopApp.controllers.Rest;


import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"Books"})
@Tag(name = "Books", description = "Сервис управления книгами")
public class BooksRestApiController {

    private final BookService bookService;

    @Autowired
    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/by-author")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<List<Book>> booksByAuthor(@ApiParam(value = "Имя автора", required = true) String authorName) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
    }

    @GetMapping("/books/by-title")
    @ApiOperation("get books by book title")
    public ResponseEntity<List<Book>> booksByTitle(@ApiParam(value = "title", required = true)  String title){
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("get book by price range from min price to max price")
    public ResponseEntity<List<Book>> priceRangeBooks(@ApiParam(value = "Минимальное значение", required = true) Integer min,
                                                      @ApiParam(value = "Максимальное значение", required = true) Integer max) {
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min, max));
    }

    @GetMapping("/books/with-max-discount")
    @ApiOperation("get list of book with max price")
    public ResponseEntity<List<Book>> maxPriceBooks() {
        return ResponseEntity.ok(bookService.getBooksWithMaxPriceDiscount());
    }

    @GetMapping("/books/bestsellers")
    @ApiOperation("get bestseller books (which is_bestseller = 1)")
    public ResponseEntity<List<Book>> bestSellerBooks() {
        return ResponseEntity.ok(bookService.getBestsellers());
    }

}
