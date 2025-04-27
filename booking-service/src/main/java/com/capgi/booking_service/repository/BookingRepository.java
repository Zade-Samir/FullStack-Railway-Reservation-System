package com.capgi.booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgi.booking_service.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	//fetches bookings made by a particular user.
	List<Booking> findByUserEmail(String email);
	
	//fetches all bookings for a particular train.
	List<Booking> findByTrainNumber(String trainNumber);

}
