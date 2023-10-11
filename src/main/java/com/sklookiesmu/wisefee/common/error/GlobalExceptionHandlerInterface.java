package com.sklookiesmu.wisefee.common.error;

import com.google.api.Http;
import com.sklookiesmu.wisefee.common.exception.common.FileDownloadException;
import com.sklookiesmu.wisefee.common.exception.common.FileUploadException;
import com.sklookiesmu.wisefee.common.exception.consumer.SubscribeExpireException;
import com.sklookiesmu.wisefee.common.exception.global.AlreadyExistElementException;
import com.sklookiesmu.wisefee.common.exception.global.AuthForbiddenException;
import com.sklookiesmu.wisefee.common.exception.global.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.common.exception.global.PreconditionFailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

public interface GlobalExceptionHandlerInterface {

    /**
     * [NoSuchElementFoundException 404]
     * 해당 자원을 찾을 수 없는 경우
     */
    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException noSuchElementFoundException, WebRequest request);

    /**
     * [AlreadyExistElementException 409]
     * 이미 존재하는 자원과 중복되는 경우
     */
    @ExceptionHandler(AlreadyExistElementException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<Object> handleAlreadyExistElementException(AlreadyExistElementException alreadyExistElementException, WebRequest request);

    /**
     * [AuthForbiddenException 403]
     * 접근 권한이 없는 경우 (인증/인가)
     */
    @ExceptionHandler(AuthForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<Object> handleAuthForbiddenException(AuthForbiddenException authForbiddenException, WebRequest request);

    /**
     * [FileDownloadException 404]
     * 파일 다운로드에 실패한 경우
     */
    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<Object> handleFileDownloadException(FileDownloadException fileDownloadException, WebRequest request);

    /**
     * [FileUploadException 500]
     * 파일 업로드에 실패한 경우
     */
    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<Object> handleFileUploadException(FileUploadException fileUploadException, WebRequest request);

    /**
     * [PreconditionFailException 412]
     * 요청이 조건에 부합하지 않는 경우
     */
    @ExceptionHandler(PreconditionFailException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    ResponseEntity<Object> handlePreconditionFailException(PreconditionFailException preconditionFailException, WebRequest request);

    /**
     * [SubscribeExpireException 404]
     * 만료된 구독권에 접근하려는 경우
     */
    @ExceptionHandler(SubscribeExpireException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<Object> handleSubscribeExpireException(SubscribeExpireException subscribeExpireException, WebRequest request);

}
