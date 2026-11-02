package com.humanresource.humanresourcemanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//not use
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String statusText;
    private int statusCode;
    private String message;
    private T data;
    private Meta meta;



}
