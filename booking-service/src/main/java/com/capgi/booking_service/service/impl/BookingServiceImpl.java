package com.capgi.booking_service.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgi.booking_service.TrainClient;
import com.capgi.booking_service.dto.BookingRequest;
import com.capgi.booking_service.dto.Train;
import com.capgi.booking_service.exception.BookingNotFoundException;
import com.capgi.booking_service.model.Booking;
import com.capgi.booking_service.repository.BookingRepository;
import com.capgi.booking_service.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private TrainClient trainClient;
	
	@Override
	public Booking createBooking(BookingRequest bookingRequest) {
		
		Train train = trainClient.getTrainByNumber(bookingRequest.getTrainNumber());		
		
		if (train.getAvailableSeats() < bookingRequest.getNumberOfTickets()) {
			throw new RuntimeException("No available seats for this train.");
		}
		
		// Fill booking details from train response
		Booking booking = new Booking();
		
	    booking.setUserEmail(bookingRequest.getUserEmail());
	    booking.setTrainNumber(train.getTrainNumber());
	    booking.setTrainName(train.getTrainName());
	    booking.setSource(train.getSource());
	    booking.setDestination(train.getDestination());
	    booking.setJourneyDate(bookingRequest.getJourneyDate());
	    booking.setNumberOfTickets(bookingRequest.getNumberOfTickets());
	    booking.setAvailableSeats(train.getAvailableSeats() - bookingRequest.getNumberOfTickets());
	    booking.setBookingDate(LocalDate.now());
	    booking.setStatus("CONFIRMED");
		
		
		return bookingRepository.save(booking);
	}
	
	@Override
	public List<Booking> getBookingsByUserEmail(String email) {
		return bookingRepository.findByUserEmail(email);
	}
	
	@Override
	public List<Booking> getBookingsByTrainNumber(String trainNumber) {
		return bookingRepository.findByTrainNumber(trainNumber);
	}
	
	@Override
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public void cancelBooking(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with Id: " + id));
		
		bookingRepository.delete(booking);
	}

}
