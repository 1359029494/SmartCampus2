package com.qilu.exception;

import com.qilu.utils.JsonData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonData handlerException(Exception e){
        if(e instanceof CustomException){
            CustomException customException=(CustomException)e;
            return JsonData.buildError(customException.getMsg(), customException.getCode());
        }else{
            return JsonData.buildError(e.getMessage());
        }
    }
}
