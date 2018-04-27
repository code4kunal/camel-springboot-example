package com.example.project.springboot.routes;

import com.example.project.springboot.dao.Vehicle;
import com.example.project.springboot.exception.VehicleNotFoundException;
import com.example.project.springboot.model.VehicleRequest;
import com.example.project.springboot.processor.EditVehicleRequestProcessor;
import com.example.project.springboot.processor.JwtRequestProcessor;
import com.example.project.springboot.service.JwtService;
import com.example.project.springboot.service.VehicleService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;


@Component
public class VehicleRoutes extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    restConfiguration()
        // to use undertow component and run on port 8081
        .component("restlet").port(8081);



//    // error handling to return custom HTTP status codes for the various exceptions
//    onException(InvalidVehicleException.class)
//        .handled(true)
//        // use HTTP status 400 when input data is invalid
//        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
//        .setBody(constant(""));

    onException(VehicleNotFoundException.class)
        .handled(true)
        // use HTTP status 404 when data was not found
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
        .setBody(constant(""));

    onException(Exception.class)
        .handled(true)
        // use HTTP status 500 when we had a server side error
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
        .setBody(simple("${exception.message}\n"));


    rest("/v1")
        .get("/viewvehicles")
        .responseMessage().code(200).message("List of vehicles").endResponseMessage()
        .responseMessage().code(500).message("Server error").endResponseMessage()
        .to("direct:viewvehicles")

        .get("/viewvehicles/{id}").outType(Vehicle.class)
        .param().name("id").description("The vehicle id").endParam()
        .responseMessage().code(200).message("The vehicle with the given id").endResponseMessage()
        .responseMessage().code(404).message("Vehicle not found").endResponseMessage()
        .responseMessage().code(500).message("Server error").endResponseMessage()
        .to("direct:veiwvehicle")


        .post("/addvehicle").type(Vehicle.class).outType(String.class)
        .responseMessage().code(200).message("The created order id").endResponseMessage()
        .responseMessage().code(400).message("Invalid input data").endResponseMessage()
        .responseMessage().code(500).message("Server error").endResponseMessage()
        .to("direct:addvehicle")


        .put("/editvehicle/{id}").outType(Vehicle.class)
        .responseMessage().code(200).message("The created order id").endResponseMessage()
        .responseMessage().code(400).message("Invalid input data").endResponseMessage()
        .responseMessage().code(500).message("Server error").endResponseMessage()
        .to("direct:editvehicle")


        .delete("/deletevehicle/{id}").outType(String.class)
        .responseMessage().code(404).message("Vehicle not found").endResponseMessage()
        .responseMessage().code(500).message("Server error").endResponseMessage()
        .to("direct:deletevehicle");


    rest("/generate-token")
        .get("/").to("direct:generate-token");


    from("direct:generate-token").bean(JwtService.class, "createJWT(1,issuer,subject,5000000)");

    from("direct:addvehicle").process(new JwtRequestProcessor()).unmarshal(new JacksonDataFormat(VehicleRequest.class)).
        bean(VehicleService.class, "createVehicle").marshal().json(JsonLibrary.Jackson);

    from("direct:editvehicle").process(new JwtRequestProcessor()).unmarshal(new JacksonDataFormat(VehicleRequest.class))
        .process(new EditVehicleRequestProcessor())
        .bean(VehicleService.class, "editVehicle(${body}").marshal().json(JsonLibrary.Jackson);

    from("direct:deletevehicle").bean(VehicleService.class, "deleteVehicle(${header.id})");

    from("direct:viewvehicles").bean(VehicleService.class, "findAllVehicles()").
        marshal().json(JsonLibrary.Jackson);

    from("direct:veiwvehicle").bean(VehicleService.class, "findVehicle(${header.id})").
        marshal().json(JsonLibrary.Jackson);


  }
}
