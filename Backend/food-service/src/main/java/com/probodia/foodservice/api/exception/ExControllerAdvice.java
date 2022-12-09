package com.probodia.foodservice.api.exception;

import com.probodia.foodservice.api.annotation.BasicError;
import com.probodia.foodservice.api.annotation.ValidationError;
import com.probodia.foodservice.api.common.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchEnumValue.class)
    @BasicError
    public ErrorResult unAuthorizedTokenHandle(NoSuchEnumValue e){
        return new ErrorResult("No such Enum value.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExcelRowException.class)
    @BasicError
    public ErrorResult excelRowException(ExcelRowException e){
        return new ErrorResult("Cannot found Row.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @BasicError
    public ErrorResult excelRowException(IllegalArgumentException e){
        return new ErrorResult("Cannot found Argument.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    @BasicError
    public ErrorResult unAuthorizedTokenHandle(UnAuthorizedException e){
        return new ErrorResult("Unauthorized", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ValidationError
    public ErrorResult handleValidationExceptions(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(c -> errors.put(((FieldError)c).getField() , c.getDefaultMessage()));

        return new ErrorResult("Request is not valid", errors);
    }

}
