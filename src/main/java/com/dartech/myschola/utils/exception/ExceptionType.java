package com.dartech.myschola.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found", "Entity Not Found"),
    DUPLICATE_ENTITY("duplicate", "Entity Already Exists"),
    INTERNAL_ERROR("internal.server.error", "Internal Server Error");

    private final String code;
    private final String message;

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
