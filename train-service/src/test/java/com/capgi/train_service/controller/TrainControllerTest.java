package com.capgi.train_service.controller;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.capgi.train_service.model.Train;
import com.capgi.train_service.service.TrainService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class TrainControllerTest {
	
	@Mock
	private TrainService trainService;
	
	@InjectMocks
	private TrainController trainController;
	
	private Train train;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		train = new Train("12345", "Express", "A", "B", "08:00 AM", "02:00 PM", 100, 100);
	}
	
	@Test
	void testAddTrain() {
		when(trainService.addTrain(train)).thenReturn(train);
		ResponseEntity<Train> response = trainController.addTrain(train);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode()); //201 means created
		assertEquals(train, response.getBody());
		verify(trainService, times(1)).addTrain(train);
	}
	
	@Test
	void testGetAllTrains() {
		List<Train> trains = Arrays.asList(train);	
		when(trainService.getAllTrains()).thenReturn(trains);
		
		ResponseEntity<List<Train>> response = trainController.getAllTrains();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(trains, response.getBody());
		verify(trainService, times(1)).getAllTrains();
	}
	
	@Test
	void testGetTrainByNumber_Found() {
		when(trainService.getTrainByTrainNumber("12345")).thenReturn(train);
		
		ResponseEntity<Train> response = trainController.getTrainByNumber("12345");
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(train, response.getBody());
		verify(trainService, times(1)).getTrainByTrainNumber("12345");
	}
	
	@Test
	void testGetTrainByNumber_NotFound() {
		when(trainService.getTrainByTrainNumber("99999")).thenReturn(null);
		
		ResponseEntity<Train> response = trainController.getTrainByNumber("99999");
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
		verify(trainService, times(1)).getTrainByTrainNumber("99999");
		
	}
	
	@Test
	void testDeleteTrain() {
		doNothing().when(trainService).deleteTrain("12345");
		
		ResponseEntity<Void> response = trainController.deleteTrain("12345");
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(trainService, times(1)).deleteTrain("12345");
	}
	
	
	

}

































