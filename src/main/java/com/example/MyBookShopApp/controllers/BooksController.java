package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.annotations.UserActionToCartLoggable;
import com.example.MyBookShopApp.data.BookRatingRequestData;
import com.example.MyBookShopApp.data.BookReviewLikeValue;
import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookReview;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.services.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Date;

import static com.example.MyBookShopApp.data.book.Book2Type.TypeStatus.ARCHIVED;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final ResourceStorage storage;
    private final BookReviewService bookReviewService;
    private final RatingService ratingService;
    private final RecentlyViewedBooksService recentlyViewedBooksService;
    private final BookReviewLikeService reviewLikeService;

    private final static String BOOKS_REDIRECT = "redirect:/books/";

    @Autowired
    public BooksController(BookService bookService, ResourceStorage storage, BookReviewService bookReviewService,
                           RatingService ratingService, RecentlyViewedBooksService recentlyViewedBooksService,
                           BookReviewLikeService reviewLikeService) {
        this.bookService = bookService;
        this.storage = storage;
        this.bookReviewService = bookReviewService;
        this.ratingService = ratingService;
        this.recentlyViewedBooksService = recentlyViewedBooksService;
        this.reviewLikeService = reviewLikeService;
    }

    @GetMapping("/{slug}")
    public String bookPage(
            @PathVariable("slug") String slug,
            Model model,
            @AuthenticationPrincipal BookstoreUserDetails user) {
        Book book = bookService.findBookBySlug(slug);
        if (nonNull(user)) recentlyViewedBooksService.addRecentlyViewedBooksToUser(book, user.getBookstoreUser());
        model.addAttribute("slugBook", book);
        model.addAttribute("rating", ratingService.findBookById(book.getId()));
        model.addAttribute("ratingTotalAndAvg", ratingService.getTotalAndAvgStars(book.getId()));
        if(nonNull(model.asMap().get("noPaid"))){
            boolean isPaid = (boolean) model.asMap().get("noPaid");
            if(isPaid) model.addAttribute("errorArchive", "Вы не можете убрать в архив не купленную книгу");
        }
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookService.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.save(bookToUpdate); //save new path in db here

        return (BOOKS_REDIRECT + slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        MediaType mediaType = storage.getBookFileMime(hash);
        byte[] data = storage.getBookFileByteArray(hash);

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
        BookReview review = new BookReview(null, book, user.getBookstoreUser().getId(), user.getBookstoreUser().getName(), new Date(),
                reviewText, ratingReview == null ? 1 : ratingReview, Collections.emptyList());
        bookReviewService.saveReview(review);

        return BOOKS_REDIRECT + slug;
    }

    @PostMapping("/changeBookStatus/review/{slug}")
    public String handleChangeBookStatus(
            @RequestBody BookRatingRequestData bookRatingRequestData, @PathVariable("slug") String slug) {
        ratingService.saveRating(ratingService.findBookById(bookService.findBookBySlug(slug).getId()), bookRatingRequestData.getValue());
        return BOOKS_REDIRECT + slug;
    }

    @PostMapping("/rateBookReview/{bookSlug}")
    public String handleBookReviewRateChanging(@RequestBody BookReviewLikeValue reviewLikeValue,
                                               @PathVariable("bookSlug") String bookSlug,
                                               @AuthenticationPrincipal BookstoreUserDetails user) {
        if (nonNull(user)) {
            reviewLikeService.saveReviewLike(user.getBookstoreUser(), reviewLikeValue.getReviewid(), reviewLikeValue.getValue());
        }
        return "redirect:/books/" + bookSlug;
    }

    @PostMapping("/changeBookStatus/archive/{slug}")
    @UserActionToCartLoggable
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @AuthenticationPrincipal BookstoreUserDetails user,
            final RedirectAttributes redirectAttrs) {
        Book book = bookService.findBookBySlug(slug);
        if (bookService.isPaid(book, user.getBookstoreUser().getId(), true)) {
            bookService.saveBook2User(book, user.getBookstoreUser(), ARCHIVED);
            return "redirect:/books/" + slug;
        }
        redirectAttrs.addFlashAttribute("noPaid", true);
        return "redirect:/books/" + slug ;
    }

}
