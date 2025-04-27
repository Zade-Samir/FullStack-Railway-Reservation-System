package com.capgi.booking_service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capgi.booking_service.dto.Train;

@FeignClient(name = "TRAIN-SERVICE")
@RequestMapping("/api/train")
public interface TrainClient {
	
	@GetMapping
    List<Train> getAllTrains();

    @GetMapping("/{trainNumber}")
    Train getTrainByNumber(@PathVariable String trainNumber);

    @PostMapping("/add")
    Train addTrain(@RequestBody Train train);

    @DeleteMapping("/{trainNumber}")
    void deleteTrain(@PathVariable String trainNumber);
}

