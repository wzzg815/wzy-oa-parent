package com.wzy.common.exception;

import com.wzy.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    //全局异常处理方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行全局异常处理方法。。。");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    //特定异常处理
    public Result error(ArithmeticException arithmeticException){
        arithmeticException.printStackTrace();
        return Result.fail().message("执行特定异常处理...");
    }

    @ExceptionHandler(DivideZeroException.class)
    @ResponseBody
    //特定异常处理
    public Result error(DivideZeroException divideZeroException){
        divideZeroException.printStackTrace();
        return Result.fail().code(divideZeroException.getCode()).message(divideZeroException.getMessage());
    }
}
