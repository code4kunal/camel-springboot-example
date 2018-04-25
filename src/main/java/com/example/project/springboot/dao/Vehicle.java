package com.example.project.springboot.dao;

import com.example.project.springboot.model.VehicleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_name")
    private String uniqueName;

    @Column(name = "company")
    private String company;

    @Column(name = "mileage")
    private BigDecimal mileage;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private String category;

    @Column(name = "year_of_manufacturing")
    private Integer yearOfManufacturing;

    public Vehicle(VehicleRequest vehicleRequest){
        this.category = vehicleRequest.getCategory();
        this.company = vehicleRequest.getCompany();
        this.mileage = vehicleRequest.getMileage();
        this.price = vehicleRequest.getPrice();
        this.uniqueName = vehicleRequest.getUniqueName();
        this.yearOfManufacturing = vehicleRequest.getYearOfManufacturing();
    }

    public Vehicle map(VehicleRequest vehicleRequest){
        this.category = vehicleRequest.getCategory();
        this.company = vehicleRequest.getCompany();
        this.mileage = vehicleRequest.getMileage();
        this.price = vehicleRequest.getPrice();
        this.uniqueName = vehicleRequest.getUniqueName();
        this.yearOfManufacturing = vehicleRequest.getYearOfManufacturing();
        return this;
    }
}
