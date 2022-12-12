package az.online.shop.http.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "az.online.shop.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    //Обработчик ошибок для rest
//    @ExceptionHandler
//    public void hey() {
//        System.out.println();
//    }
}