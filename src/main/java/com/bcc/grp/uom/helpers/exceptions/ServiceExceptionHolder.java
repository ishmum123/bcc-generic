package com.bcc.grp.uom.helpers.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public class ServiceExceptionHolder {

    @Getter
    @RequiredArgsConstructor
    public static class ServiceException extends RuntimeException {
        private final int code;
        private final String message;
    }

    public static class ResourceNotFoundException extends ServiceException {
        public ResourceNotFoundException(String message) {
            super(2000, message);
        }
    }

    public static class UomNotFoundException extends ResourceNotFoundException {
        public UomNotFoundException(String uuid) {
            super("No Uom found with ID: " + uuid);
        }
    }

}
