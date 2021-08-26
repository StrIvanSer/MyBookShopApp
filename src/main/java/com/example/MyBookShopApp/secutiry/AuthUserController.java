package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.SmsCode;
import com.example.MyBookShopApp.data.UserUpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;
    private final SmsService smsService;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister, SmsService smsService) {
        this.userRegister = userRegister;
        this.smsService = smsService;
    }

    @GetMapping("/signin")
    public String handleSignIn() {
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(
            @RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        if (payload.getContact().contains("@")) {
            return response;
        } else {
            smsService.editContactConfirmation(payload);
            return response;
        }

    }

    @PostMapping("/requestEmailConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestEmailConfirmation(
            @RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        smsService.editEmailConfirmation(payload);
        response.setResult("true");
        return response;

    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(
            @RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        if (smsService.verifyCode(payload.getCode())) {
            response.setResult("true");
        }
        return response;

    }

    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model) {
        if (nonNull(userRegister.registerNewUser(registrationForm))) {
            model.addAttribute("regOk", true);
        } else {
            model.addAttribute("regError", true);

        }
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload
            , HttpServletResponse httpServletResponse
    ) {
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;
    }

    @PostMapping("/login-by-phone-number")
    @ResponseBody
    public ContactConfirmationResponse handleLoginByPhoneNumber(@RequestBody ContactConfirmationPayload payload
            , HttpServletResponse httpServletResponse
    ) {
        if (smsService.verifyCode(payload.getCode())) {
            ContactConfirmationResponse loginResponse = userRegister.jwtLoginByPhoneNumber(payload);
            Cookie cookie = new Cookie("token", loginResponse.getResult());
            httpServletResponse.addCookie(cookie);
            return loginResponse;
        } else {
            return null;
        }
    }

    @PostMapping("/profile/edit")
    public String editProfile(
            @RequestParam("phone") String phone,
            @RequestParam("mail") String mail,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("passwordReply") String passwordReply,
            Model model,
            @AuthenticationPrincipal BookstoreUserDetails user) throws MessagingException {
        model.addAttribute("curUsr", userRegister.getCurrentUser());
        model = userRegister.
                editProfile(user.getBookstoreUser(), phone, mail, name, password, passwordReply, model);
        return "profile";
    }

    @RequestMapping(value="/verification_token/{token}", method=RequestMethod.GET)
    public String verificationToken(@PathVariable("token") String token){
        UserUpdateData updateData = userRegister.getUpdateUser(token);
        if(isNull(updateData)){
            return "redirect:/profile";
        }
        userRegister.updateUser(updateData);
        return "redirect:/logout";
    }

}
