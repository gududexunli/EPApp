package com.bl.ep.handler;

import com.bl.ep.constant.ResultEnum;
import com.bl.ep.constant.ResultModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultModel defaultErrorHandler(Model model, Exception e) throws Exception{
        e.printStackTrace();
        return ResultModel.error(ResultEnum.ERROR_500);
    }
}
