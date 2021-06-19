package com.example.MyBookShopApp.services;


import com.example.MyBookShopApp.annotations.MethodDurationLoggable;
import com.example.MyBookShopApp.data.book.RatingBook;
import com.example.MyBookShopApp.data.book.RatingCount;
import com.example.MyBookShopApp.data.book.RatingCountI;
import com.example.MyBookShopApp.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

//    public RatingBook findBookBySlug(String slug) {
//        return bookRepository.findBookBySlug(slug).getRating();
//    }

    public void saveRating(RatingBook ratingBook, Integer value) {
        switch (value) {
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
            default:
                ratingBook.setOneStar(ratingBook.getOneStar() + 1);
                break;
        }
        ratingRepository.save(ratingBook);
    }


    public RatingBook findBookById(Integer bookId) {
        return ratingRepository.findByBookId(bookId);
    }

    @MethodDurationLoggable
    public RatingCount getTotalAndAvgStars(Integer bookId){
        RatingCountI totalAndAvgStars = ratingRepository.getTotalAndAvgStars(bookId);
        return new RatingCount(totalAndAvgStars.getTotal(), totalAndAvgStars.getAverage());
    }


}
