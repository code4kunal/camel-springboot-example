package com.example.project.springboot.service;

import com.example.project.springboot.dao.Vehicle;
import com.example.project.springboot.exception.VehicleNotFoundException;
import com.example.project.springboot.model.VehicleRequest;

public interface VehicleFactoryService {

  Vehicle findVehicle(Long vehicleId) throws VehicleNotFoundException;

  Vehicle editVehicle(VehicleRequest vehicleRequest);

  Vehicle createVehicle(VehicleRequest vehicleRequest);

  String deleteVehicle(String vehicleId);
}
