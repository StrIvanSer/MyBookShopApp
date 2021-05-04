package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.UserUpdateData;
import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.repo.BookstoreUserRepository;
import com.example.MyBookShopApp.secutiry.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class BookstoreUserRegister {

    private final BookstoreUserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;

    //

    @Autowired
    public BookstoreUserRegister(BookstoreUserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager, BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    //
    public BookstoreUser registerNewUser(RegistrationForm registrationForm) {
        BookstoreUser userByEmail = bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getEmail());
        BookstoreUser userByPhone = bookstoreUserRepository.findBookstoreUserByPhone(registrationForm.getPhone());
        if (userByEmail == null && userByPhone == null) {
            BookstoreUser user = new BookstoreUser();
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            bookstoreUserRepository.save(user);
            return user;
        } else {
            return userByPhone;
        }
    }

    //
    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));
        BookstoreUserDetails userDetails = (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public BookstoreUser getCurrentUser() {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getBookstoreUser();
    }

    public ContactConfirmationResponse jwtLoginByPhoneNumber(ContactConfirmationPayload payload) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setPhone(payload.getContact());
        registrationForm.setPass(payload.getCode());
        registerNewUser(registrationForm);
        BookstoreUserDetails userDetails = (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public void saveUser(BookstoreUser user) {
        bookstoreUserRepository.save(user);
    }

    public String encodePass(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isChange(BookstoreUser user, String phone, String mail, String name) {
        return !(user.getEmail().equals(mail) && user.getName().equals(name)
                && user.getPhone().equals(phone));
    }

    public Model checkPassword(Model model, String password, String passwordReply) {
        if (password.length() < 6) {
            model.addAttribute("passErrorSize", "Пароль менее 6 символов");
        } else if (!password.equals(passwordReply)) {
            model.addAttribute("passError", "Пароли не совпадают");
        }
        return model;
    }

    public void updateUser(UserUpdateData updateData) {
        BookstoreUser user = bookstoreUserRepository.getOne(updateData.getUserId());
        user.setEmail(updateData.getEmail());
        user.setPhone(updateData.getPhone());
        user.setName(updateData.getName());
        if(!updateData.getPassword().equals("")){
            user.setPassword(passwordEncoder.encode(updateData.getPassword()));
        }
        bookstoreUserRepository.save(user);
    }

}
