package com.example.project.springboot.processor;

import com.example.project.springboot.model.ApiResponseSuccess;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

public class ResponseProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getIn();

        String status = "400";
        Object obj = message.getBody();

        ApiResponseSuccess apiResponseSuccess = new ApiResponseSuccess(status, obj);
        message.setBody(apiResponseSuccess);
        exchange.setOut(message);
    }
}
