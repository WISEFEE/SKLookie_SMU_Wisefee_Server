package com.sklookiesmu.wisefee.common.error;

import com.sklookiesmu.wisefee.common.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public interface GlobalExceptionHandlerInterface {

    /**
     * [NoSuchElementFoundException 404]
     * 해당 자원을 찾을 수 없는 경우
     */
    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException noSuchElementFoundException, WebRequest request);

    /**
     * [AlreadyExistElementException 409]
     * 이미 존재하는 자원과 중복되는 경우
     */
    @ExceptionHandler(AlreadyExistElementException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleAlreadyExistElementException(AlreadyExistElementException alreadyExistElementException, WebRequest request);

    /**
     * [AuthForbiddenException 403]
     * 접근 권한이 없는 경우 (인증/인가)
     */
    @ExceptionHandler(AuthForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAuthForbiddenException(AuthForbiddenException authForbiddenException, WebRequest request);

    /**
     * [FileDownloadException 404]
     * 파일 다운로드에 실패한 경우
     */
    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleFileDownloadException(FileDownloadException fileDownloadException, WebRequest request);

    /**
     * [FileUploadException 500]
     * 파일 업로드에 실패한 경우
     */
    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleFileUploadException(FileUploadException fileUploadException, WebRequest request);

    /**
     * [PreconditionFailException 412]
     * 요청이 조건에 부합하지 않는 경우
     */
    @ExceptionHandler(PreconditionFailException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Object> handlePreconditionFailException(PreconditionFailException preconditionFailException, WebRequest request);
}
