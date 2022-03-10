package app.isparks.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author： chenghd
 * @date： 2021/2/25
 */
@ControllerAdvice
public class HandlerControllerExceptionAdvice {


    @ExceptionHandler(value = TemplateInputException.class)
    public String error(HttpServletRequest request,Exception e){

        System.out.println("出错了。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");

        return "index";
    }

    @ExceptionHandler(value = TemplateProcessingException.class)
    public String error1(HttpServletRequest request,Exception e){

        System.out.println("出错了。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");

        return "index";
    }



}
