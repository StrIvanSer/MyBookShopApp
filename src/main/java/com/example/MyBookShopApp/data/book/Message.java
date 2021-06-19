package com.example.MyBookShopApp.data.book;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "message")
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date time;
    @Column(name = "user_id")
    private Integer userId;
    private String email;
    private String name;
    private String subject;
    private String text;

}
