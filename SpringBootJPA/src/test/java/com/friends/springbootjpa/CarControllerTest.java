package com.friends.springbootjpa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.friends.springbootjpa.exceptions.ResourceNotFoundException;
import com.friends.springbootjpa.models.Car;
import com.friends.springbootjpa.repository.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CarControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@MockBean
	private CarRepository carRepository;
	
	private List<Car> cars;
	
	@Before
	public void init() {
		cars = new ArrayList<Car>() ;
		cars.add(new Car(1, "Fiat", "Yellow", 4));
		cars.add(new Car(2, "Accord", "Red", 4));
		cars.add(new Car(3, "Benz", "Yellow", 4));
	}
	
	@Test
	public void find_allCars_OK() {
		when(carRepository.findAll()).thenReturn(cars);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/cars", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void find_carByID_OK() {
		when(carRepository.findById(1L)).thenReturn(Optional.of(cars.get(0)));
		ResponseEntity<Car> response = restTemplate.getForEntity("/api/v1/cars/1", Car.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cars.get(0).getCarName(), response.getBody().getCarName());
	}
	
	@Test
	public void find_CarById_NotFound() {
		ResponseEntity<Car> response = null;
		try {
			when(carRepository.findById(1L)).thenThrow(new ResourceNotFoundException("Car is not available"));
			response = restTemplate.getForEntity("/api/v1/cars/4", Car.class);
		} catch (Exception exception) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
