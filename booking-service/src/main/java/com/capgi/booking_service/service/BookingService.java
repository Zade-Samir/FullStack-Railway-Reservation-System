package com.capgi.booking_service.service;

import java.util.List;

import com.capgi.booking_service.dto.BookingRequest;
import com.capgi.booking_service.model.Booking;

public interface BookingService {
	
	Booking createBooking(BookingRequest bookingRequest);
	List<Booking> getBookingsByUserEmail(String email);
    List<Booking> getBookingsByTrainNumber(String trainNumber);
    List<Booking> getAllBookings();
	void cancelBooking(Long id); 

}
