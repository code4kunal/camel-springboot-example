package com.example.project.springboot.component;

import com.example.project.springboot.dao.Vehicle;
import com.example.project.springboot.model.VehicleRequest;
import com.example.project.springboot.processor.EditVehicleRequestProcessor;
import com.example.project.springboot.processor.JwtRequestProcessor;
import com.example.project.springboot.processor.ResponseProcessor;
import com.example.project.springboot.service.JwtService;
import com.example.project.springboot.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.logging.log4j.LogManager;
import org.restlet.data.Form;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.NoSuchElementException;


@Component
public class VehicleRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost").port(8081);
        restConfiguration().component("restlet");

        onException(NoSuchElementException.class).handled(true)
                // use HTTP status 404 when input data is invalid
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))

            ;

        rest("/v1")
                .get("/viewvehicles").to("direct:viewvehicles").produces("application/json")

                .post("/addvehicle").to("direct:addvehicle")

                .get("/viewvehicles/{id}")
                .to("direct:viewvehicle")


                .post("/editvehicle/{id}").to("direct:editvehicle")

                .delete("/deletevehicle/{id}").to("direct:deletevehicle");

        rest("/generate-token")
            .get("/").to("direct:generate-token");


        from("direct:generate-token").bean(JwtService.class, "createJWT(1,issuer,subject,5000000)");;

        from("direct:addvehicle").process(new JwtRequestProcessor()).unmarshal(new JacksonDataFormat(VehicleRequest.class)).
                bean(VehicleService.class, "createVehicle").marshal().json(JsonLibrary.Jackson);

        from("direct:editvehicle").process(new JwtRequestProcessor()).unmarshal(new JacksonDataFormat(VehicleRequest.class))
                .process(new EditVehicleRequestProcessor())
                .bean(VehicleService.class, "editVehicle(${body}").marshal().json(JsonLibrary.Jackson);

        from("direct:deletevehicle").bean(VehicleService.class, "deleteVehicle(${header.id})");

        from("direct:viewvehicles").bean(VehicleService.class, "findAllVehicles()").
                marshal().json(JsonLibrary.Jackson);
        from("direct:viewvehicle")
                .bean(VehicleService.class, "findVehicle(${header.id})").
                marshal().json(JsonLibrary.Jackson);


    }
}
