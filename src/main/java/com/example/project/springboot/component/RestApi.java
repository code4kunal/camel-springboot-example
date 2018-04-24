package com.example.project.springboot.component;

import com.example.project.springboot.controller.HelloController;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestApi extends RouteBuilder {
    @Override
    public void configure() throws Exception {

    }
}
