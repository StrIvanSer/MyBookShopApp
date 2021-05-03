package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.SmsCode;
import com.example.MyBookShopApp.repo.SmsCodeRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsService {

    @Value("${twilio.ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${twilio.AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Value("${twilio.TWILIO_NUMBER}")
    private String TWILIO_NUMBER;

    private final SmsCodeRepository smsCodeRepository;

    @Autowired
    public SmsService(SmsCodeRepository smsCodeRepository) {
        this.smsCodeRepository = smsCodeRepository;
    }

    public String sendSecretCodeSms(String contact){
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        String formattedContact = contact.replaceAll("[()-]", "");
        String generatedCode = generateCode();
        Message.creator(
                new PhoneNumber(formattedContact),
                new PhoneNumber(TWILIO_NUMBER),
                "Ваш код подтверждения: " + generatedCode
        ).create();
        return generatedCode;
    }

    public String generateCode() {
        //nnn nnn
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length()<6){
            stringBuilder.append(random.nextInt(9));
        }
        stringBuilder.insert(3," ");
        return stringBuilder.toString();
    }

    public void saveNewCode(SmsCode smsCode){
        if(smsCodeRepository.findByCode(smsCode.getCode())==null){
            smsCodeRepository.save(smsCode);
        }
    }

    public Boolean verifyCode(String code){
        SmsCode smsCode = smsCodeRepository.findByCode(code);
        return smsCode !=null && !smsCode.isExpired();
    }
}
