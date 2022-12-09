package com.probodia.challengeservice.api.exception;

import com.probodia.challengeservice.api.common.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchEnumValue.class)
    public ErrorResult unAuthorizedTokenHandle(NoSuchEnumValue e){
        return new ErrorResult("No such Enum value.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalJoinException.class)
    public ErrorResult excelRowException(IllegalJoinException e){
        return new ErrorResult("Join after challenge start.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult excelRowException(IllegalArgumentException e){
        return new ErrorResult("Cannot found Argument.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ErrorResult unAuthorizedTokenHandle(UnAuthorizedException e){
        return new ErrorResult("Unauthorized", e.getMessage());
    }


}
