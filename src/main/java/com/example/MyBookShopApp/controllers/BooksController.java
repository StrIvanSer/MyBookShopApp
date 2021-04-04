package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookReview;
import com.example.MyBookShopApp.data.book.RatingBook;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.services.BookReviewService;
import com.example.MyBookShopApp.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final ResourceStorage storage;
    private final BookReviewService bookReviewService;
    private final RatingService ratingService;

    @Autowired
    public BooksController(BookRepository bookRepository, ResourceStorage storage, BookReviewService bookReviewService,
                           RatingService ratingService) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.bookReviewService = bookReviewService;
        this.ratingService = ratingService;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model) {
        Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("slugBook", book);
        book.getRating().getAvgStar();
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate); //save new path in db here

        return ("redirect:/books/" + slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    @PostMapping("/addReview/{slug}")
    public String addReview(
            @RequestParam("reviewAuthor") String reviewAuthor,
            @RequestParam("reviewText") String reviewText,
            @RequestParam("ratingReview") Integer ratingReview,
            @PathVariable("slug") String slug
    ) {
        Book book = bookRepository.findBookBySlug(slug);
        BookReview review = new BookReview();
        review.setUserName(reviewAuthor);
        review.setTime(new Date());
        review.setBook(book);
        review.setText(reviewText);
        review.setUserId(0);
        review.setRating(ratingReview);
        bookReviewService.saveReview(review);

        return "redirect:/books/" + slug;
    }

    @PostMapping("/changeBookStatus/review/{slug}")
    public String handleChangeBookStatus(
            @RequestParam("value") Integer value,
            @PathVariable("slug") String slug) {
        ratingService.saveRating(ratingService.findBookBySlug(slug), value);
        return "redirect:/books/" + slug;
    }
}
