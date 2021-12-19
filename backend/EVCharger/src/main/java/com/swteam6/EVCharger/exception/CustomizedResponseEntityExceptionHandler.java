package com.swteam6.EVCharger.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Author: Song Hak Hyeon
 * Spring AOP 기능을 활용한 에러 handling으로 서비스 내에서 발생할 수 있는 예외들에 대해
 * 각각 다른 Http Status Code와 해당하는 에러 log를 Client에 반환하기 위한 용도입니다.
 */

@RestController
@ControllerAdvice // 전역 설정을 위한 annotation
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @Valid 또는 @Validated 를 사용하여 유효성 검사를 하게 되면, 모든 예외 처리보다 우선순위가 높은데
     * 이 상황에서 MethodArgumentNotValidException 을 커스텀으로 처리하고자 하면
     * Bean Creation Exception 이 발생하거나 default로 설정된 500 Internal Server Error가 상태코드로 지정됨.
     * 이런 문제를 피하고자 500 Internal Server Error 대신 default error 를 400 Bad Request 로 처리하였음.
     * (handleAllExceptions 에서 전역 에러 핸들링이 발생함)
     */
    // 현재 시스템에서 대부분의 에러는 입력값에 대한 클라이언트 에러만 발생할 수 밖에 없는 구조라서 다음과 같이 전역 처리를 함.
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 찾을 수 없는 회원: UserNotFoundException 이 발생하는 경우 처리됨.
     */
    // 특정 에러: 없는 회원 조회
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), "찾을 수 없는 회원입니다.", request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 이미 등록된 회원: email을 기준으로 중복 되는 회원이 있는 경우 EmailDuplicationException 이 발생하여 처리됨.
     */
    // 특정 에러: 중복 회원 여부 처리
    @ExceptionHandler(EmailDuplicationException.class)
    public final ResponseEntity<Object> handleEmailDuplicationExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), "이메일 중복", request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
}
