package com.example.project.springboot.service;

import com.example.project.springboot.dao.Vehicle;
import com.example.project.springboot.model.VehicleRequest;
import com.example.project.springboot.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehiclesRepository vehiclesRepository;

    public List<Vehicle> findAllVehicles(){
        return (List<Vehicle>)vehiclesRepository.findAll();
    }

    public Vehicle findVehicle(Long id){
        return vehiclesRepository.findById(id).get();
    }

    public Vehicle createVehicle(VehicleRequest vehicleRequest){
        Vehicle vehicle = new Vehicle(vehicleRequest);
        vehicle = vehiclesRepository.save(vehicle);
        return vehicle;
    }

    public Vehicle editVehicle( VehicleRequest vehicleRequest){
        Long id = new Long(vehicleRequest.getId());
        Vehicle vehicle = vehiclesRepository.findById(id).get();
        vehicle = vehicle.map(vehicleRequest);
        vehicle = vehiclesRepository.save(vehicle);
        return vehicle;
    }

    public String deleteVehicle(String ids){
        Long id = new Long(ids);
        vehiclesRepository.deleteById(id);
        return "Deleted " + ids;
    }

}
