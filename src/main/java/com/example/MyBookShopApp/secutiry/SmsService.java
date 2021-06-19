package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.SmsCode;
import com.example.MyBookShopApp.repo.SmsCodeRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SmsService {

    private final JavaMailSender javaMailSender;
    private final SmsCodeRepository smsCodeRepository;

    @Autowired
    public SmsService(JavaMailSender javaMailSender, SmsCodeRepository smsCodeRepository) {
        this.javaMailSender = javaMailSender;
        this.smsCodeRepository = smsCodeRepository;
    }

    @Value("${twilio.ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${twilio.AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Value("${twilio.TWILIO_NUMBER}")
    private String TWILIO_NUMBER;

    public String generateCode() {
        //nnn nnn
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < 6) {
            stringBuilder.append(random.nextInt(9));
        }
        if (verifyCode(stringBuilder.toString())) {
            generateCode();
        }
        stringBuilder.insert(3, " ");
        return stringBuilder.toString();
    }

    public void saveNewCode(SmsCode smsCode) {
        if (smsCodeRepository.findByCode(smsCode.getCode()) == null) {
            smsCodeRepository.save(smsCode);
        }
    }

    public Boolean verifyCode(String code) {
        String replace = code.replace(" ", "");
        SmsCode smsCode = smsCodeRepository.findByCode(replace);
        return smsCode != null && !smsCode.isExpired();
    }

    public String sendSecretCodeSms(String contact) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String formattedContact = contact.replaceAll("[()-]", "");
        String generatedCode = generateCode();
        Message.creator(
                new PhoneNumber(formattedContact),
                new PhoneNumber(TWILIO_NUMBER),
                "Ваш код подтверждения: " + generatedCode
        ).create();
        return generatedCode;
    }

    public void editEmailConfirmation(ContactConfirmationPayload payload) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tspring@internet.ru");
        message.setTo(payload.getContact());
        String generatedCode = generateCode();
        SmsCode smsCode = new SmsCode(generatedCode, 300);//5 min
        saveNewCode(smsCode);
        message.setSubject("Bookstore email ver");
        message.setText("Ver cod is : " + generatedCode);
        javaMailSender.send(message);
    }


    public void editContactConfirmation(ContactConfirmationPayload payload) {
        String smsCodeString = sendSecretCodeSms(payload.getContact());
        saveNewCode(new SmsCode(smsCodeString, 60));
    }
}
