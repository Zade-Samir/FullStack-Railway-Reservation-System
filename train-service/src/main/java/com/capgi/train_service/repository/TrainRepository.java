package com.capgi.train_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.capgi.train_service.model.Train;

public interface TrainRepository extends JpaRepository<Train, String> {
    
	Train findByTrainNumber(String trainNumber);
}
