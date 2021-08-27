package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.UserUpdateData;
import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.data.book.BookstoreUserDto;
import com.example.MyBookShopApp.repo.BookstoreUserRepository;
import com.example.MyBookShopApp.secutiry.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.mail.MessagingException;

@Service
public class BookstoreUserRegister {

    private static final String PASS_ERROR = "passErrorSize";
    private static final String ERROR = "passError";

    private final BookstoreUserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final UpdateUserService updateUserService;

    //

    public BookstoreUserRegister(BookstoreUserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager, BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil, UpdateUserService updateUserService) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.updateUserService = updateUserService;
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
            model.addAttribute(PASS_ERROR, "Пароль менее 6 символов");
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
        if (!updateData.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(updateData.getPassword()));
        }
        bookstoreUserRepository.save(user);
        updateUserService.deleteUpdateData(updateData);
    }


    public Model editProfile(BookstoreUser user, String phone, String mail, String name, String password,
                             String passwordReply, Model model) throws MessagingException {

        if (!isChange(user, phone, mail, name) && password.equals("")) {
            model.addAttribute("noChange", true);
            return model;
        }

        if (!password.equals("") && !isChange(user, phone, mail, name)) {
            model = checkPassword(model, password, passwordReply);
            if (!model.containsAttribute(PASS_ERROR) && !model.containsAttribute(ERROR)) {
                user.setPassword(encodePass(password));
                saveUser(user);
                model.addAttribute("change", true);
            }
            return model;
        }

        if (!password.equals("")) {
            model = checkPassword(model, password, passwordReply);
            if (model.containsAttribute(PASS_ERROR) || model.containsAttribute(ERROR)) {
                return model;
            }
        }
        updateUserService.sendEmailConfirm(phone, mail, name, password, user.getId());
        model.addAttribute("changeAccept", true);
        model.addAttribute("acceptMessage",
                "Для подтверждения изменений необходимо перейти по ссылку, которая отправлена вам на email: " + mail);
        return model;
    }

    public UserUpdateData getUpdateUser(String token) {
        return updateUserService.getUpdateUser(token);
    }

    public BookstoreUserDto getUserDtoById(Integer id) {
        BookstoreUser user = bookstoreUserRepository.getOne(id);
        return new BookstoreUserDto(user);
    }


    public Model editProfileByAdmin(BookstoreUserDto userDto, Integer id, Model model) {
        BookstoreUser user = bookstoreUserRepository.getOne(id);
        if (!isChange(user, userDto.getPhone(), userDto.getEmail(), userDto.getName()) && userDto.getPassword().equals("")) {
            model.addAttribute("noChange", true);
            return model;
        }

        if (!userDto.getPassword().equals("")) {
            model = checkPassword(model, userDto.getPassword(), userDto.getPasswordConfirm());
            if (model.containsAttribute(PASS_ERROR) || model.containsAttribute(ERROR)) {
                return model;
            }
        }
        user.setPassword(encodePass(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        bookstoreUserRepository.save(user);
        model.addAttribute("changeAccept", true);
        model.addAttribute("acceptEditMessage", "Данные успешно изменены");
        return model;

    }

}