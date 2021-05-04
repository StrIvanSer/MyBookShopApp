package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.UserUpdateData;
import com.example.MyBookShopApp.repo.UserUpdateDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UpdateUserService {

    private final JavaMailSender javaMailSender;
    private final UserUpdateDataRepository userUpdateDataRepository;

    @Autowired
    public UpdateUserService(JavaMailSender javaMailSender, UserUpdateDataRepository userUpdateDataRepository) {
        this.javaMailSender = javaMailSender;
        this.userUpdateDataRepository = userUpdateDataRepository;
    }

    public void sendEmailConfirm(String phone, String mail, String name, String password, Integer userId) throws MessagingException {
        String token = generateCode();
        UserUpdateData updateData = new UserUpdateData();
        updateData.setToken(token);
        updateData.setUserId(userId);
        updateData.setEmail(mail);
        updateData.setPhone(phone);
        updateData.setPassword(password);
        updateData.setName(name);
        updateData.setExpireTime(LocalDateTime.now());
        userUpdateDataRepository.save(updateData);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = setMessageText(phone, mail, name, password, token);
        helper.setFrom("tspring@internet.ru");
        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(mail);
        helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
        javaMailSender.send(mimeMessage);
    }

    private String setMessageText(String phone, String mail, String name, String password, String code) {
        String message = "";
        message = "<h3>Ваша учетная запись изменена!</h3>" + "\n" +
                "<h4>Mail: " + mail + "</h4>\n" +
                "<h4>Phone: " + phone + "</h4>\n" +
                "<h4>Name: " + name + "</h4>\n";
        if (!password.equals("")) {
            message += "<h4>Новый пароль: " + password + "</h4>\n";
        } else {
            message += "<h4>Пароль без изменений" + "</h4>\n";
        }
        message += "<h4>Для подтверждения изменений перейдите по ссылке, после перехода необходимо заново авторизоваться!!! <h4>" + generateLink(code);
        return message;

    }

    private String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String generateLink(String token){
        return  "<a href='http://localhost:8085/verification_token/" + token +"'> Согласен, погнали</a>";
    }

    public UserUpdateData getUpdateUser(String token){
        return userUpdateDataRepository.findUserUpdateDataByToken(token);
    }

    public void deleteUpdateData(UserUpdateData updateData) {
        userUpdateDataRepository.delete(updateData);
    }
}
