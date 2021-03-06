package com.example.MyBookShopApp.controllers.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;


@Setter
@Getter
public class ApiResponse<T>  extends RepresentationModel<ApiResponse<T>> {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    private String message;
    private String debugMessage;
    private Collection<T> data;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

}
