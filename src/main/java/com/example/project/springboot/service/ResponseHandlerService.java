package com.example.project.springboot.service;

import com.example.project.springboot.model.ApiResponse;
import com.example.project.springboot.model.ApiResponseSuccess;
import org.springframework.stereotype.Service;

@Service
public class ResponseHandlerService {

    public ApiResponse responseHandle(Object object){
        return new ApiResponseSuccess("status", object);
    }
}
