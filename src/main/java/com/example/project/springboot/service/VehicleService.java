package com.example.project.springboot.service;

import com.example.project.springboot.dao.Vehicle;
import com.example.project.springboot.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Autowired
    private VehiclesRepository vehiclesRepository;

    public Vehicle findAllVehicles(){
        return null;
    }

}
