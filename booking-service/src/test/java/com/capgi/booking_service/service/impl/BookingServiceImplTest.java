package com.capgi.booking_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgi.booking_service.TrainClient;
import com.capgi.booking_service.dto.BookingRequest;
import com.capgi.booking_service.dto.Train;
import com.capgi.booking_service.model.Booking;
import com.capgi.booking_service.repository.BookingRepository;

public class BookingServiceImplTest {
	
	@Mock
	private BookingRepository bookingRepository;
	
	@Mock
	private TrainClient trainClient;
	
	@InjectMocks
	private BookingServiceImpl bookingServiceImpl;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testCreateBooking_Success() {
		BookingRequest bookingRequest = new BookingRequest(
	            "user@example.com", "12345", "Test Express", "A", "B",
	            LocalDate.of(2025, 4, 20), 2
	        );
		
		Train train = new Train("12345", "Test Express", "A", "B", "08:00 AM", "02:00 PM", 100, 50);
		
		//all data from above
		Booking savedBooking = new Booking();
		savedBooking.setId(1L);
        savedBooking.setUserEmail("user@example.com");
        savedBooking.setTrainNumber("12345");
        savedBooking.setTrainName("Test Express");
        savedBooking.setSource("A");
        savedBooking.setDestination("B");
        savedBooking.setJourneyDate(LocalDate.of(2025, 4, 20));
        savedBooking.setNumberOfTickets(2);
        savedBooking.setAvailableSeats(48);
        savedBooking.setBookingDate(LocalDate.now());
        savedBooking.setStatus("CONFIRMED");
        
        when(trainClient.getTrainByNumber("12345")).thenReturn(train);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        
        //test
        Booking result = bookingServiceImpl.createBooking(bookingRequest);
        
        //check the cases
        assertNull(result);
        assertEquals("12345", result.getTrainNumber());
        assertEquals(2, result.getNumberOfTickets());
        assertEquals("CONFIRMED", result.getStatus());
		
	}
	
	@Test
	public void testGetBookingsByUserEmail() {
		
		String email = "user@example.com";
		Booking booking = new Booking();
		booking.setUserEmail(email);
		
		when(bookingRepository.findByUserEmail(email)).thenReturn(List.of(booking));
		
		List<Booking> result = bookingServiceImpl.getBookingsByUserEmail(email);
		
		//checking the test cases
		assertEquals(1, result.size());
		assertEquals(email, result.get(0).getUserEmail());
	}
	
	@Test
	public void testGetBookingsByTrainNumber() {
		
		String trainNumber = "12345";
		Booking booking = new Booking();
		booking.setTrainNumber(trainNumber);
		
		when(bookingRepository.findByTrainNumber(trainNumber)).thenReturn(List.of(booking));
		List<Booking> result = bookingServiceImpl.getBookingsByTrainNumber(trainNumber);
		
		//checking the test cases
		assertEquals(1, result.size());
		assertEquals(trainNumber, result.get(0).getTrainNumber());
	}
	
	@Test
	public void testGetAllBookings() {
		Booking booking1 = new Booking();
		Booking booking2 = new Booking();
		
		when(bookingRepository.findAll()).thenReturn(List.of(booking1, booking2));
		
		List<Booking> result = bookingServiceImpl.getAllBookings();
		
		//check the test cases
		assertEquals(2, result.size());
	}
	
	@Test
	public void testCancelBooking_Success() {
		Long bookingId = 1L;
		Booking booking = new Booking();
		booking.setId(bookingId);
		
		 when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
		 
		 bookingServiceImpl.cancelBooking(bookingId);
		 
		 //check the test case
		 verify(bookingRepository, times(1)).delete(booking);
	}
	
	@Test
	public void testCancelBooking_NotFound() {
		Long bookingId = 100L;
		Booking booking = new Booking();
		booking.setId(bookingId);
		
		when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
	        bookingServiceImpl.cancelBooking(bookingId);
	    });
		
		assertTrue(exception.getMessage().contains("Booking not found with Id"));
		
	}

}


























