package com.example.cars.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModelProperty;

/**
 * This class will represent our car and its attributes:
 * - ID
 * - Name
 * - Number of doors
 * 
 * @author Robley Gori - ro6ley.github.io
 */
@Entity
@Table(name = "cars")   // the table in the database that will contain our cars data
@EntityListeners(AuditingEntityListener.class)
public class Car {
  
  /**
   * The attributes of the car
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;    // Each car will be given an auto-generated unique identifier when stored

  @Column(name = "car_name", nullable = false)
  @NotNull(message="Car Name should not be null")
  private String carName;    // We will also save the name of the car
  
  @NotNull(message="Color should not be null")
  private String color;   

  @Column(name = "doors", nullable = false)
  @Min(value = 1, message="Doors should not be null")
  private int doors;    // We will also save the number of doors that a car has

  /**
   * Our getters and setters for the attributes above
   */
  @ApiModelProperty(name="id",
                    value="The id of the car",
                    example="1")
  public long getId() {
    return id;
  }

  public void setId(long value) {
    this.id = value;
  }

  @ApiModelProperty(name="carName",
                    value="The name of the car to be saved",
                    example="Bugatti",
                    required=true)
  public String getCarName() {
    return carName;
  }

  public void setCarName(String value) {
    this.carName = value;
  }
  
  @ApiModelProperty(name="doors",
                    value="The number of doors that the car has",
                    example="2",
                    required=true)
  public int getDoors() {
    return doors;
  }

  public void setDoors(int value) {
    this.doors = value;
  }

public Car(long id, @NotNull(message = "Car Name should not be null") String carName,
		@NotNull(message = "Color should not be null") String color,
		@Min(value = 1, message = "Doors should not be null") int doors) {
	super();
	this.id = id;
	this.carName = carName;
	this.color = color;
	this.doors = doors;
}
  
public Car() {};
  

}
