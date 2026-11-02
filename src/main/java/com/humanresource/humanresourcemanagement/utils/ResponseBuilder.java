package com.humanresource.humanresourcemanagement.utils;

import com.humanresource.humanresourcemanagement.dto.ApiResponse;
import com.humanresource.humanresourcemanagement.dto.Meta;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.UUID;

public class ResponseBuilder {
    public static <T> ApiResponse<T> success(T data, String message, HttpServletRequest request) {
        var path = request.getRequestURI();
        var method = request.getMethod();
        Meta meta = new Meta(UUID.randomUUID().toString(), path, Instant.now().toString(), method);
        return new ApiResponse<>("success", HttpStatus.OK.value(), message, data, meta);
    }


    public static <T> ApiResponse<T> error(int code, String message, HttpServletRequest request) {
        var path = request.getRequestURI();
        var method = request.getMethod();
        Meta meta = new Meta(UUID.randomUUID().toString(), path, Instant.now().toString(), method);
        return new ApiResponse<>("error", code, message, null, meta);
    }

}