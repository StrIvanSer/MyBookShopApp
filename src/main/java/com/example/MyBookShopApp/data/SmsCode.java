package com.example.MyBookShopApp.data;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sms_keys")
@Setter
@Getter
public class SmsCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDateTime expireTime;

    public SmsCode(String code, Integer expireIn) {
        this.code = code.replace(" ", "");
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public SmsCode() {
    }

    public Boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }
}
