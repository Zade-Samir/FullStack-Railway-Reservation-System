package com.capgi.train_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.capgi.train_service.model.Train;
import com.capgi.train_service.service.TrainService;

@RestController
@RequestMapping("/api/train")
public class TrainController {
	
	@Autowired
	private TrainService trainService;
	
	//add new train
	@PostMapping("/add")
	public ResponseEntity<Train> addTrain(@RequestBody Train train) {
		Train savedTrain = trainService.addTrain(train);
        return new ResponseEntity<>(savedTrain, HttpStatus.CREATED);
	}
	
	//get all the trains
	@GetMapping
	public ResponseEntity<List<Train>> getAllTrains() {
		return ResponseEntity.ok(trainService.getAllTrains());
	}
	
	//get trains by trainNumber
	@GetMapping("/{trainNumber}")
	public ResponseEntity<Train> getTrainByNumber(@PathVariable String trainNumber) {
		Train train = trainService.getTrainByTrainNumber(trainNumber);
		
		if (train == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(train);
	}
	
	//delete train using trainNumber
	@DeleteMapping("/{trainNumber}")
	public ResponseEntity<Void> deleteTrain(@PathVariable String trainNumber) {
		trainService.deleteTrain(trainNumber);
		return ResponseEntity.noContent().build();
	}
	
}




















