package com.example.MyBookShopApp.services;


import com.example.MyBookShopApp.data.book.RatingBook;
import com.example.MyBookShopApp.repo.BookRepository;
import com.example.MyBookShopApp.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.bookRepository = bookRepository;
    }

    public RatingBook findBookBySlug(String slug) {
        return bookRepository.findBookBySlug(slug).getRating();
    }

    public void saveRating(RatingBook ratingBook, Integer value) {
        switch (value) {
            case 1:
                ratingBook.setOneStar(ratingBook.getOneStar() + 1);
                break;
            case 2:
                ratingBook.setTwoStart(ratingBook.getTwoStart() + 1);
                break;
            case 3:
                ratingBook.setThreeStar(ratingBook.getThreeStar() + 1);
                break;
            case 4:
                ratingBook.setFourStart(ratingBook.getFourStart() + 1);
                break;
            case 5:
                ratingBook.setFiveStar(ratingBook.getFiveStar() + 1);
                break;
        }
        ratingRepository.save(ratingBook);
    }
}
