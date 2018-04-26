package com.example.project.springboot.model;

import lombok.Getter;
import lombok.Setter;

public class ApiResponseSuccess extends ApiResponse {

    public ApiResponseSuccess(String status, Object body){
        super(status, body);
    }
}
