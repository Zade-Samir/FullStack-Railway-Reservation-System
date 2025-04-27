package com.capgi.booking_service.controller;

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

import com.capgi.booking_service.dto.BookingRequest;
import com.capgi.booking_service.model.Booking;
import com.capgi.booking_service.service.BookingService;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	//create the booking
	@PostMapping("/create")
	public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
		Booking savedBooking = bookingService.createBooking(bookingRequest);
		return ResponseEntity.ok(savedBooking);
	}
	
	// Get all bookings by user email
	@GetMapping("/user/{email}")
	public ResponseEntity<List<Booking>> getBookingByUser(@PathVariable String email) {
		List<Booking> bookings = bookingService.getBookingsByUserEmail(email);
		return ResponseEntity.ok(bookings);
	}
	
	// Get all bookings by train number
	@GetMapping("/train/{trainNumber}")
	public ResponseEntity<List<Booking>> getBookingByTrain(@PathVariable String trainNumber) {
		List<Booking> bookings = bookingService.getBookingsByTrainNumber(trainNumber);
		return ResponseEntity.ok(bookings);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> bookings = bookingService.getAllBookings();
		return ResponseEntity.ok(bookings);
	}

	//cancel the booking
	@DeleteMapping("/cancel/{id}")
	public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
		bookingService.cancelBooking(id);
		return ResponseEntity.ok("Booking Cancelled Successfully!");
	}
}














