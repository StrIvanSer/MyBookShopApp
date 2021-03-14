package com.example.MyBookShopApp.data;


import com.example.MyBookShopApp.data.Book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ApiModel(description = "data model of author entity")
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "author id generation by db", position = 1)
    private Integer id;
    @ApiModelProperty(value = "first name of author",example = "John", position = 2)
    private String firstName;
    @ApiModelProperty(value = "last name of author", example = "Ketonovich", position = 3)
    private String lastName;
    @JsonIgnore
    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();

    @Override
    public String toString() {
        return firstName  + " " + lastName;
    }
}
