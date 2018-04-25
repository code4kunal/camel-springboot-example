package com.example.project.springboot.processor;

import com.example.project.springboot.model.VehicleRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class EditVehicleRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Message msg = exchange.getIn();
        String id = msg.getHeader("id").toString();
        VehicleRequest vehicleRequest = (VehicleRequest)msg.getBody();
        vehicleRequest.setId(new Long(id));
        msg.setBody(vehicleRequest);
        exchange.setOut(msg);
        msg.getHeaders();
    }
}
