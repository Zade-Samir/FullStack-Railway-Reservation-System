package com.capgi.booking_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgi.booking_service.dto.BookingRequest;
import com.capgi.booking_service.model.Booking;
import com.capgi.booking_service.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookingControllerTest {
	
	//Main entry point for server-side Spring MVC test support. 
	private MockMvc mockMvc;
	
	//ObjectMapper provides functionality for reading and writing JSON,either to and from basic POJOs
	private ObjectMapper objectMapper;
	
	@Mock
	private BookingService bookingService;
	
	@InjectMocks
	BookingController bookingController;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
		objectMapper = new ObjectMapper();
	}
	
	@Test
	public void testCreateBooking() throws Exception {
		BookingRequest request = new BookingRequest("user@example.com", "12345", "Express", "A", "B", LocalDate.now(), 2);
        Booking booking = new Booking(1L, request.getUserEmail(), request.getTrainNumber(), request.getTrainName(),
                request.getSource(), request.getDestination(), request.getJourneyDate(), request.getNumberOfTickets(),
                98, LocalDate.now(), "CONFIRMED");
        
        when(bookingService.createBooking(any(BookingRequest.class))).thenReturn(booking);
        
        mockMvc.perform(post("/api/booking/create")
        					.contentType(MediaType.APPLICATION_JSON)
        					.content(objectMapper.writeValueAsString(request)))
        			.andExpect(status().isOk())
        			.andExpect(jsonPath("$.trainNumber").value("12345"));
        
        verify(bookingService).createBooking(any(BookingRequest.class));
	}
	
	@Test
	public void testGetBookingByUser() throws Exception {
		String email = "user@example.com";
		List<Booking> bookings = Arrays.asList(new Booking());
		when(bookingService.getBookingsByUserEmail(email)).thenReturn(bookings);
		
		mockMvc.perform(get("/api/booking/user/{email}", email))
							.andExpect(status().isOk());
		
		verify(bookingService).getBookingsByUserEmail(email);
	}
	
	@Test
	public void testGetBookingByTrain() throws Exception {
		String trainNumber = "12345";
		List<Booking> bookings = Arrays.asList(new Booking());
		when(bookingService.getBookingsByTrainNumber(trainNumber)).thenReturn(bookings);
		
		mockMvc.perform(get("/api/booking/train/{trainNumber}", trainNumber))
							.andExpect(status().isOk());
		
		verify(bookingService).getBookingsByTrainNumber(trainNumber);
	}
	
	@Test
	public void testGetAllBookings() throws Exception {
		when(bookingService.getAllBookings()).thenReturn(Arrays.asList(new Booking()));
		
		mockMvc.perform(get("/api/booking/all")).andExpect(status().isOk());
		
		verify(bookingService).getAllBookings();
	}
	
	@Test
	public void testCancelBooking() throws Exception {
	    Long id = 1L;

	    doNothing().when(bookingService).cancelBooking(id);

	    mockMvc.perform(delete("/api/booking/cancel/{id}", id))
	            .andExpect(status().isOk())
	            .andExpect(content().string("Booking Cancelled Successfully!"));

	    verify(bookingService).cancelBooking(id);
	}

	
	

}


























