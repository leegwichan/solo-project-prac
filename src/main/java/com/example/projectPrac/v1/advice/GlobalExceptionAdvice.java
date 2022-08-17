package com.example.projectPrac.v1.advice;

import com.example.projectPrac.v1.dto.ErrorResponseDto;
import com.example.projectPrac.v1.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMissingServletRequestParameterException
            (MissingServletRequestParameterException e){
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException
            (BusinessLogicException e){
        ErrorResponseDto dto = new ErrorResponseDto(e.getExceptionCode().getStatus(), e.getExceptionCode().getMessage());
        return new ResponseEntity(dto, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }
}
