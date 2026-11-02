package com.humanresource.humanresourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// not use
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta {
    private String requestId;
    private String path;
    private String timestamp;
    private String method;
}
