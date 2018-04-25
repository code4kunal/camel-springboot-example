package com.example.project.springboot.repository;

import com.example.project.springboot.dao.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclesRepository extends CrudRepository<Vehicle, Long>{

    public Vehicle findByUserName(String userName);
}
