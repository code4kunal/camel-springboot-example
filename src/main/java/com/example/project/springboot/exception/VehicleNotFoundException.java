package com.example.project.springboot.exception;


/**
 * Exception when the order was not found
 */
public class VehicleNotFoundException extends Exception {

  private Long id;

  public VehicleNotFoundException(Long id) {
    super("Vehicle not found with id " + id);
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}