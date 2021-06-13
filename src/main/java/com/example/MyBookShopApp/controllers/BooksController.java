package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookReview;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.BookReviewService;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.RatingService;
import com.example.MyBookShopApp.services.RecentlyViewedBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final ResourceStorage storage;
    private final BookReviewService bookReviewService;
    private final RatingService ratingService;
    private final RecentlyViewedBooksService recentlyViewedBooksService;

    @Autowired
    public BooksController(BookService bookService, ResourceStorage storage, BookReviewService bookReviewService,
                           RatingService ratingService, RecentlyViewedBooksService recentlyViewedBooksService) {
        this.bookService = bookService;
        this.storage = storage;
        this.bookReviewService = bookReviewService;
        this.ratingService = ratingService;
        this.recentlyViewedBooksService = recentlyViewedBooksService;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model, @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        if (nonNull(user)) recentlyViewedBooksService.addRecentlyViewedBooksToUser(book, user.getBookstoreUser());
        model.addAttribute("slugBook", book);
        model.addAttribute("rating", ratingService.findBookById(book.getId()));
        model.addAttribute("ratingTotalAndAvg", ratingService.getTotalAndAvgStars(book.getId()));
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookService.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.save(bookToUpdate); //save new path in db here

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
            @RequestParam("reviewText") String reviewText,
            @RequestParam(value = "ratingReview", required = false) Integer ratingReview,
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user
    ) {
        Book book = bookService.findBookBySlug(slug);
        BookReview review = new BookReview();
        review.setUserName(user.getBookstoreUser().getName());
        review.setTime(new Date());
        review.setBook(book);
        review.setText(reviewText);
        review.setUserId(user.getBookstoreUser().getId());
        review.setRating(ratingReview == null ? 0 : ratingReview);
        bookReviewService.saveReview(review);

        return "redirect:/books/" + slug;
    }

    @PostMapping("/changeBookStatus/review/{slug}")
    public String handleChangeBookStatus(
            @RequestParam("value") Integer value,
            @PathVariable("slug") String slug) {
        ratingService.saveRating(ratingService.findBookById(bookService.findBookBySlug(slug).getId()), value);
        return "redirect:/books/" + slug;
    }

    @GetMapping("/myarchive")
    public String myarchivePage() {
        return "myarchive";
    }
}
