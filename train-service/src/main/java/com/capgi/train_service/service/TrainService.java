package com.capgi.train_service.service;

import java.util.List;

import com.capgi.train_service.model.Train;

public interface TrainService {
	
	Train addTrain(Train train);
	List<Train> getAllTrains();
	Train getTrainByTrainNumber(String trainNumber);
    void deleteTrain(String trainNumber);

}
