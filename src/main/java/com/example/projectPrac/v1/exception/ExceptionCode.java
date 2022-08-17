package com.example.projectPrac.v1.exception;

import lombok.Getter;

public enum ExceptionCode {

    USER_NOT_FOUND(404, "USER_NOT_FOUND");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}
