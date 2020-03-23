package com.friends.springbootjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.friends.springbootjpa.models.Car;

/**
 * The interface Car repository.
 *
 * @author Robley Gori - ro6ley.github.io
 */
public interface CarRepository extends JpaRepository<Car, Long> {
  
}
