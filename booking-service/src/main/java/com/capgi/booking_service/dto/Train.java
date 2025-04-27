package com.capgi.booking_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train {
	
	private String trainNumber;
	private String trainName;
	private String source;
	private String destination;
	private String departureTime;
	private String arrivalTime;
	private int totalSeats;
	private int availableSeats;


}
