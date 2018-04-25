package com.example.project.springboot.component;

import com.example.project.springboot.service.JwtService;
import com.example.project.springboot.service.VehicleService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class VehicleRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost").port(8081);
        restConfiguration().component("restlet");

        rest("/v1")
                .get("/viewvehicles").to("direct:viewvehicles").produces("application/json")
                .post("/addvehicle").to("direct:addvehicle")
                .get("/viewvehicles/{id}").to("direct:veiwvehicle")
                .post("/editvehicle/{id}").to("direct:editvehicle")
                .delete("/deletevehicle/{id}").to("direct:deletevehicle");

        rest("/generate-token")
            .get("/").to("direct:generate-token");


        from("direct:hello").bean(JwtService.class, "validate(${body}, ${header.Authorization})");
        from("direct:bye").transform().simple("Bye ${header.id}");
        from("direct:generate-token").bean(JwtService.class, "createJWT(1,issuer,subject,5000000)");;
        from("direct:addvehicle").bean(JwtService.class, "validate(${header.Authorization}, ${body})");;
        from("direct:editvehicle").bean(JwtService.class, "validate(${body})");;
        from("direct:deletevehicle").bean(JwtService.class, "validate(${body})");;
        from("direct:viewvehicles").bean(VehicleService.class, "findAllVehicles()").
                marshal().json(JsonLibrary.Jackson);
        from("direct:veiwvehicle").bean(VehicleService.class, "findVehicle(${header.id})").
                marshal().json(JsonLibrary.Jackson);


    }
}
