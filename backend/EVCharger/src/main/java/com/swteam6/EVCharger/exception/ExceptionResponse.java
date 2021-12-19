package com.swteam6.EVCharger.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 예외 처리용 클래스
 * http 상태코드를 담아서 클라이언트에 반환 용도
 *
 * timestamp: 에러가 발생한 시간
 * message: 에러 메시지
 * details: 에러 발생 log
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
