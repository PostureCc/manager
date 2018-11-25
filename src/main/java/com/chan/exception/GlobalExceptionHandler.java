package com.chan.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**全局异常处理*/
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 所有异常报错
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public String allExceptionHandler(HttpServletRequest request, Exception exception) {
        try {
            exception.printStackTrace();
            return "500";
        } catch (Exception e) {
            System.out.println("exception " + exception.getMessage());
            return "500";
        }

    }

}