package com.capgi.train_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgi.train_service.model.Train;
import com.capgi.train_service.repository.TrainRepository;

public class TrainServiceImplTest {
	
	
	@Mock //create fake version of repo (dependencies)
	private TrainRepository trainRepository;
	
	//injectMocks - injecting the above mocks into this instance.
	@InjectMocks 
	private TrainServiceImpl trainService;
	
	//Before every test runs, @Mock and @InjectMocks are reset and started fresh.
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testAddTrain() {
		Train train = new Train("12345", "Express", "A", "B", "08:00 AM", "02:00 PM", 100, 100);
		
		when(trainRepository.save(train)).thenReturn(train);
		Train result = trainService.addTrain(train);
		
		assertNotNull(result);
		assertEquals("12345", result.getTrainNumber());
		verify(trainRepository, times(1)).save(train);
	}
	
	@Test
	void testGetAllTrains() {
		Train train1 = new Train("12345", "Express", "A", "B", "08:00 AM", "02:00 PM", 100, 90);
        Train train2 = new Train("54321", "Local", "C", "D", "09:00 AM", "12:00 PM", 80, 80);
        List<Train> trainList = Arrays.asList(train1, train2);
        
        when(trainRepository.findAll()).thenReturn(trainList);
        List<Train> result = trainService.getAllTrains();
        
        assertEquals(2, result.size());
        verify(trainRepository, times(1)).findAll();
	}
	
	@Test
	void testGetTrainByTrainNumber() {
		Train train = new Train("12345", "Express", "A", "B", "08:00 AM", "02:00 PM", 100, 80);
		
		when(trainRepository.findByTrainNumber("12345")).thenReturn(train);
		Train result = trainService.getTrainByTrainNumber("12345");
		
		assertNotNull(result);
		assertEquals("Express", result.getTrainName());
		verify(trainRepository, times(1)).findByTrainNumber("12345");
	}
	
	@Test
	void testDeleteTrain() {
		String trainNumber = "12345";
		doNothing().when(trainRepository).deleteById(trainNumber);
		
		trainService.deleteTrain(trainNumber);
		
		verify(trainRepository, times(1)).deleteById(trainNumber);
	}

}
























