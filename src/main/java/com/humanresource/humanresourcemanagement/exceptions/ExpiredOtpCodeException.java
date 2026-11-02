package com.humanresource.humanresourcemanagement.exceptions;

public class ExpiredOtpCodeException extends RuntimeException {
    public ExpiredOtpCodeException(String message) {
        super(message);
    }
}
