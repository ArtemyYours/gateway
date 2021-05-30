package ru.balacetracker.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {

    private final HttpStatus status;
    private final ExceptionDto exceptionDto;

    public HttpException(HttpStatus status, MessageCode messageCode, Object errorParameters, Throwable throwable) {
        this.status = status;
        this.exceptionDto = new ExceptionDto(
                throwable,
                messageCode,
                errorParameters,
                throwable.getMessage()
        );
    }

    public HttpException(HttpStatus status, ExceptionDto exceptionDto) {
        this.status = status;
        this.exceptionDto = exceptionDto;
    }

    public HttpException(HttpStatus status, MessageCode messageCode, Object errorParameters, String message) {
        this.status = status;
        this.exceptionDto = new ExceptionDto(
                new IllegalArgumentException(message),
                messageCode,
                errorParameters,
                message
        );
    }

    @Override
    public String getMessage() {
        return exceptionDto.getMessage();
    }
}
