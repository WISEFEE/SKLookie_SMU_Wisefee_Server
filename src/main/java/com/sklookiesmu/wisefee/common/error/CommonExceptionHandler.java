//package com.sklookiesmu.wisefee.common.error;
//
//
//import com.sklookiesmu.wisefee.dto.common.error.CustomErrorResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.server.ResponseStatusException;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//@Slf4j
//public class CommonExceptionHandler {
//
//    @ExceptionHandler(ResponseStatusException.class)
//    @ResponseBody
//    public CustomErrorResponse handleCustomHttpException(ResponseStatusException ex, HttpServletRequest request) {
//        CustomErrorResponse errorResponse = new CustomErrorResponse();
//        errorResponse.setTimestamp(LocalDateTime.now());
//        errorResponse.setStatus(ex.getStatus().value());
//        errorResponse.setError(ex.getStatus().getReasonPhrase());
//        errorResponse.setMessage(ex.getReason());
//
//        String requestURL = request.getRequestURI();
//        String requestMethod = request.getMethod();
//
////        String logMessage = String.format("[%s/%s] : %s : %s",
////                requestMethod, ex.getStatus(), requestURL, errorResponse.getMessage());
////        log.warn(logMessage);
//        return errorResponse;
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public CustomErrorResponse handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
//        CustomErrorResponse errorResponse = new CustomErrorResponse();
//        errorResponse.setTimestamp(LocalDateTime.now());
//        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        errorResponse.setMessage("Internal Server Error");
//
//        String requestURL = request.getRequestURI();
//        String requestMethod = request.getMethod();
//
//        String stackTrace = Arrays.stream(ex.getStackTrace())
//                .map(StackTraceElement::toString)
//                .collect(Collectors.joining(System.lineSeparator())); // 개행으로 구분
//        String logMessage = String.format("[%s/%s] : %s : %s \n%s",
//                requestMethod, errorResponse.getStatus(), requestURL, errorResponse.getMessage(), stackTrace);
//        log.warn(logMessage);
//        return errorResponse;
//    }
//}
