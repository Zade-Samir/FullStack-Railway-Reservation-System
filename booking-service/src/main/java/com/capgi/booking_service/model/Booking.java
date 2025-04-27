package com.capgi.booking_service.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userEmail;
	private String trainNumber;
	private String trainName;
    private String source;
    private String destination;
    private LocalDate journeyDate;
    private int numberOfTickets;
    private int availableSeats;
    private LocalDate bookingDate;
    private String status;

}
