package com.example.project.springboot.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class VehicleRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost").port(8081);
        restConfiguration().component("restlet");

        rest("/vehicle")
                .get("/").to("direct:hello")
                .get("/{id}").to("direct:bye");

        rest("/users").description("Users REST service")
                .get("/").description("The list of all the users")
                .route().routeId("users-api")
                .to("sql:select  * from users?" +
                        "dataSource=dataSource&" +
                        "outputClass=com.example.project.springboot.dao.Users")
                .endRest();

        from("direct:hello").transform().constant("Hello World");
        from("direct:bye").transform().simple("Bye ${header.id}");
    }
}
