package com.humanresource.humanresourcemanagement.exceptions;

public class InvalidOtpCodeException extends RuntimeException {
    public InvalidOtpCodeException(String message) {
        super(message);
    }
}
