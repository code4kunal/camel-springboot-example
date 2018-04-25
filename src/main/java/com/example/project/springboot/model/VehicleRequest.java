package com.example.project.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {
    private Long id;
    private String uniqueName;
    private String company;
    private BigDecimal mileage;
    private BigDecimal price;
    private String category;
    private Integer yearOfManufacturing;
}
