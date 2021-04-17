package com.example.MyBookShopApp.controllers.rest;


import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.errors.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    private void appendAuthorLink(ApiResponse<Book> response, List<Book> books) {
        for (final Book book : books) {
            Link selfLink = linkTo(methodOn(AuthorsRestApiController.class)
                    .getAuthor(book.getAuthor().getId())).withRel("author");
            book.add(selfLink);
        }
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + books.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(books);
    }

    @GetMapping("/books/by-title")
    @ApiOperation("get books by book title")
    public ResponseEntity<ApiResponse<Book>> booksByTitle(@ApiParam(value = "title", required = true) String title)
            throws BookstoreApiWrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksByTitle(title);
        appendAuthorLink(response, data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/by-author")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<ApiResponse<Book>> booksByAuthor(@ApiParam(value = "Имя автора", required = true) String authorName) {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksByAuthor(authorName);
        for (final Book book : data) {
            Link selfLink = linkTo(methodOn(BooksRestApiController.class)
                    .getBookById(book.getId())).withRel("book");
            book.add(selfLink);
        }
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("get book by price range from min price to max price")
    public ResponseEntity<List<Book>> priceRangeBooks(@ApiParam(value = "Минимальное значение", required = true) Integer min,
                                                      @ApiParam(value = "Максимальное значение", required = true) Integer max) {
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min, max));
    }

    @GetMapping("/books/with-max-discount")
    @ApiOperation("get list of book with max price")
    public ResponseEntity<ApiResponse<Book>> maxPriceBooks() {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksWithMaxPriceDiscount();
        appendAuthorLink(response, data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/bestsellers")
    @ApiOperation("get bestseller books (which is_bestseller = 1)")
    public ResponseEntity<ApiResponse<Book>> bestSellerBooks() {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBestsellers();
        appendAuthorLink(response, data);
        return ResponseEntity.ok(response);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Missing required parameters",
                exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Bad parameter value...", exception)
                , HttpStatus.BAD_REQUEST);
    }

    @GetMapping("book/{id:\\d+}")
    @ApiOperation("get book by title")
    public Book getBookById(@PathVariable final Integer id) {
        return bookService.getBookById(id);
    }

}
