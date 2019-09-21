package com.qilu.exception;

import com.qilu.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class HandlerException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonData handlerException(Exception e){
        if(e instanceof CustomException){
            CustomException customException=(CustomException)e;
            log.info("customerError:{}",((CustomException) e).getMsg());
            return JsonData.buildError(customException.getMsg(), customException.getCode());
        }else{
            log.error("error:{}",e.getMessage());
            return JsonData.buildError("未知异常，联系管理员");
        }
    }
}
