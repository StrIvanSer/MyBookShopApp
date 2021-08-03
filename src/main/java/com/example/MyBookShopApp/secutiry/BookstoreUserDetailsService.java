package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.repo.BookstoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final BookstoreUserRepository bookstoreUserRepository;

    @Autowired
    public BookstoreUserDetailsService(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        BookstoreUser bookstoreUser = bookstoreUserRepository.findBookstoreUserByEmail(s);
        if (bookstoreUser != null) {
            return new BookstoreUserDetails(bookstoreUser);
        }
        bookstoreUser = bookstoreUserRepository.findBookstoreUserByPhone(s);

        if (bookstoreUser != null) {
            return new PhoneNumberBookstoreUserDetails(bookstoreUser);
        } else {
            throw new UsernameNotFoundException("user not found doh!");
        }
    }

    public List<BookstoreUser> getAllUser() {
        return bookstoreUserRepository.findAll();
    }
}
