package com.capgi.train_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgi.train_service.model.Train;
import com.capgi.train_service.repository.TrainRepository;
import com.capgi.train_service.service.TrainService;

@Service
public class TrainServiceImpl implements TrainService {
	
	@Autowired
	private TrainRepository trainRepository;

	
	@Override
	public Train addTrain(Train train) {
		
		return trainRepository.save(train);
	}

	@Override
	public List<Train> getAllTrains() {
		
		return trainRepository.findAll();
	}

	@Override
	public Train getTrainByTrainNumber(String trainNumber) {
		
		return trainRepository.findByTrainNumber(trainNumber);
	}

	@Override
	public void deleteTrain(String trainNumber) {
		
		trainRepository.deleteById(trainNumber);
	}
	
	

}
