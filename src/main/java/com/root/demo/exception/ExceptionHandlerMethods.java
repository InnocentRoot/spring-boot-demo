package com.root.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class ExceptionHandlerMethods {
    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }

    public static class UnSupportedFieldPatchException extends RuntimeException {
        public UnSupportedFieldPatchException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public class ServerException extends RuntimeException {
        public ServerException(String message) {
            super(message);
        }

        public ServerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
