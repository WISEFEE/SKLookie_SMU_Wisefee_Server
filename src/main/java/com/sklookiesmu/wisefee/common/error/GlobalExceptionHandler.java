package com.sklookiesmu.wisefee.common.error;


import com.sklookiesmu.wisefee.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements GlobalExceptionHandlerInterface {

    public static final String TRACE = "trace";

    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

    // Validate
    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.", LocalDateTime.now());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    // NoSuchElementFoundException
    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException noSuchElementFoundException, WebRequest request) {
        log.error("Failed to find the request element", noSuchElementFoundException);
        return buildErrorResponse(noSuchElementFoundException, HttpStatus.NOT_FOUND, request);
    }

    // AlreadyExistElementException
    @ExceptionHandler(AlreadyExistElementException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleAlreadyExistElementException(AlreadyExistElementException alreadyExistElementException, WebRequest request) {
        log.error("Failed to element is already exist", alreadyExistElementException);
        return buildErrorResponse(alreadyExistElementException, HttpStatus.CONFLICT, request);
    }

    // AuthForbiddenException
    @ExceptionHandler(AuthForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAuthForbiddenException(AuthForbiddenException authForbiddenException, WebRequest request) {
        log.error("Failed to Auth fail", authForbiddenException);
        return buildErrorResponse(authForbiddenException, HttpStatus.FORBIDDEN, request);
    }

    // FileDownloadException
    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleFileDownloadException(FileDownloadException fileDownloadException, WebRequest request) {
        log.error("Failed to while file download", fileDownloadException);
        return buildErrorResponse(fileDownloadException, HttpStatus.NOT_FOUND, request);
    }

    // FileUploadException
    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleFileUploadException(FileUploadException fileUploadException, WebRequest request) {
        log.error("Failed to while file upload", fileUploadException);
        return buildErrorResponse(fileUploadException, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // PreconditionFailException
    @ExceptionHandler(PreconditionFailException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Object> handlePreconditionFailException(PreconditionFailException preconditionFailException, WebRequest request) {
        log.error("Failed to doesn't match precondition", preconditionFailException);
        return buildErrorResponse(preconditionFailException, HttpStatus.PRECONDITION_FAILED, request);
    }

    // 필요시 ExceptionHandler 추가 - 예상가는 오류 있다면 전부 ExceptionHandler 이용해 처리.



    // Uncaught Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
        log.error("Internal error occurred", exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      String message,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message, LocalDateTime.now());
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return buildErrorResponse(ex, status, request);
    }
}