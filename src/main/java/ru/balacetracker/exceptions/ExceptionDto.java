package ru.balacetracker.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.annotation.Nullable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionDto {

    private UserErrorMessage userErrorMessage;
    private List<UserErrorMessage> userErrorMessageList;
    private String message;
    private String exception;
    private String cause;
    private String rootCause;

    public ExceptionDto(Throwable throwable, MessageCode messageCode, Object errorParameters, String message) {
        this.userErrorMessage = new UserErrorMessage(
                messageCode == MessageCode.UNKNOWN ? null : messageCode.getKey(),
                errorParameters
        );
        this.message = message;
        this.exception = combineIntoMessage(throwable);
        this.cause = combineIntoMessage(ExceptionUtils.getCause(throwable));
        this.rootCause = combineIntoMessage(ExceptionUtils.getRootCause(throwable));
    }

    private String combineIntoMessage(@Nullable Throwable throwable) {
        return throwable == null
                ? "NO_EXCEPTION"
                : throwable.getClass().getSimpleName() + " : " + throwable.getMessage();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UserErrorMessage {

        private String errorMessage;
        private Object errorParameters;

        public UserErrorMessage(MessageCode messageCode) {
            this.errorMessage = messageCode.getKey();
        }
    }

}
