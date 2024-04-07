//package com.example.orderapi.config.common.validation;
//
//import com.example.orderapi.web.validation.exception.OrderException;
//import com.example.orderapi.web.validation.exception.OrderExceptionResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@ControllerAdvice
//
//@Slf4j
//public class ValidException2 {
//
//    @ExceptionHandler({
//            MethodArgumentNotValidException.class
//    })
//    public ResponseEntity<MethodExceptionResponse> orderBadRequestException(final MethodArgumentNotValidException e){
//
//        List<String> errors = e.getBindingResult().getFieldErrors()
//                .stream()
//                .map(ex -> ex.getDefaultMessage())
//                .collect(Collectors.toList());
//        System.out.println("checking");
//        System.out.println(errors.get(0));
//
//        return ResponseEntity.badRequest().body(new MethodExceptionResponse(errors.get(0)));
//    }
//    @ExceptionHandler({
//            HttpMessageNotReadableException.class
//    })
//    public ResponseEntity<MethodExceptionResponse> orderBadRequestException2(final HttpMessageNotReadableException e){
//
//        System.out.println(e.getMessage());
//
//        return ResponseEntity.badRequest().body(new MethodExceptionResponse("메세지 형식을 확인해 주세요."));
//    }
//
//
//}
//
//
//
