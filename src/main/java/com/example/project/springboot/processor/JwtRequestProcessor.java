package com.example.project.springboot.processor;

import com.example.project.springboot.service.JwtService;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtRequestProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        Message log = exchange.getIn();
        Series h = (Series) log.getHeaders().get("org.restlet.http.headers");
        String auth = h.getFirst("Authorization").getValue().toString();

        JwtService jwtService = new JwtService();
        jwtService.validate(auth);
    }
}
