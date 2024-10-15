package com.dartech.myschola.utils.exception;

import org.springframework.http.HttpStatus;

public abstract class PredefinedExceptions {

    public static final CustomException notFoundException = new CustomException(
            ExceptionType.ENTITY_NOT_FOUND.getCode(),
            ExceptionType.ENTITY_NOT_FOUND.getMessage(),
            HttpStatus.NOT_FOUND
    );

    public static final CustomException duplicateEntity = new CustomException(
            ExceptionType.DUPLICATE_ENTITY.getCode(),
            ExceptionType.DUPLICATE_ENTITY.getMessage(),
            HttpStatus.CONFLICT
    );
}
