package com.example.MyBookShopApp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PaymentService {

    @Value("${robbokassa.mercha.login}")
    private String merchaLogin;

    @Value("${robbokassa.pass.first.test}")
    private String firstPass;

    public String getPaymentUrl(Integer sum, Long invId) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((merchaLogin + ":" + sum.toString() + ":" + invId.toString() + ":" + firstPass).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx" +
                "?MerchantLogin=" + merchaLogin +
                "&InvId=" + invId.toString()  +
                "&Culture=ru" +
                "&Encoding=utf-8" +
                "&OutSum=" + sum.toString() +
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&IsTest=1";
    }
}
