package com.dartech.myschola.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {
    ENTITY_NOT_FOUND("not_found", "Entity Not Found"),
    DUPLICATE_ENTITY("duplicate_entity", "Entity Already Exists"),
    INTERNAL_ERROR("internal_server_error", "Internal Server Error"),
    TOKEN_EXPIRED("token_expired", "Invalid or expired token");

    private final String code;
    private final String message;

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
