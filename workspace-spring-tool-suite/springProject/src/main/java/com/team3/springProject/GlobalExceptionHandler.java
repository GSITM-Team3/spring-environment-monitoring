package com.team3.springProject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<String> handleInternalServerError(HttpServerErrorException.InternalServerError ex) {
        // 500 Internal Server Error가 발생할 때 실행될 코드
        // 여기서는 간단히 문자열로 사용자에게 보여줄 메시지를 반환하는 예시
        String errorMessage = "서버에서 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";

        // 사용자에게 새로고침을 유도하는 메시지를 포함한 ResponseEntity 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

}
