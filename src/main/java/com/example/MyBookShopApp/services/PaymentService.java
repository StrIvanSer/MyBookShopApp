package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.book.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PaymentService {

    @Value("${robbokassa.mercha.login}")
    private String merchaLogin;

    @Value("${robbokassa.pass.first.test}")
    private String firstPass;

    public String getPaymentUrl(List<Book> list) throws NoSuchAlgorithmException {
        Double allSum = list.stream().mapToDouble(Book::discountPrice).sum();
        MessageDigest md = MessageDigest.getInstance("MD5");
        String invId = "5";
        md.update((merchaLogin + ":" + allSum.toString() + ":" + invId + ":" + firstPass).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx" +
                "?MerchantLogin=" + merchaLogin +
                "&InvId=" + invId +
                "&Culture=ru"+
                "&Encoding=utf-8"+
                "&OutSum="+allSum.toString() +
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase()+
                "&IsTest=1";
    }
}
