package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.Message;
import com.example.MyBookShopApp.repo.MessageRepo;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

import static java.util.Objects.nonNull;

@Controller
public class ContactController {

    private final MessageRepo messageRepo;
    private static final String NEW_MESS = "newMess";

    @Autowired
    public ContactController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping("/contacts")
    public String contactsPage(Model model) {
        if (nonNull(model.asMap().get(NEW_MESS))) {
            String responseMessage = "Уважаемый " + model.asMap().get("name") + " Сообщение принято, наша " +
                    "служба поддержки в скором времени вам ответит на вашу почту: " + model.asMap().get("mail");
            model.addAttribute(NEW_MESS, true);
            model.addAttribute("responseMessage", responseMessage);
        }
        return "contacts";
    }

    @PostMapping("/contact/addMessage")
    public String addReview(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "mail", required = false) String mail,
            @RequestParam("topic") String topic,
            @RequestParam("text") String text,
            @AuthenticationPrincipal BookstoreUserDetails user,
            final RedirectAttributes redirectAttrs
    ) {
        Message message = Message.builder()
                .id(null)
                .email(mail)
                .name(name)
                .time(new Date())
                .subject(topic)
                .userId(null)
                .text(text)
                .build();
        if (nonNull(user)) {
            message.setName(user.getBookstoreUser().getName());
            message.setEmail(user.getBookstoreUser().getEmail());
            message.setUserId(user.getBookstoreUser().getId());
        }
        messageRepo.save(message);
        redirectAttrs.addFlashAttribute("name", message.getName());
        redirectAttrs.addFlashAttribute("mail", message.getEmail());
        redirectAttrs.addFlashAttribute(NEW_MESS, true);
        return "redirect:/contacts";
    }

}