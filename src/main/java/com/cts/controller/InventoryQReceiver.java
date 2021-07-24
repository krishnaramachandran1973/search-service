package com.cts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cts.service.SearchService;
import com.cts.vo.NewBooking;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class InventoryQReceiver {
	private static final Logger logger = LoggerFactory.getLogger(InventoryQReceiver.class);
	private final SearchService searchService;
	
	@RabbitListener(queues = "InventoryQ")
	public void processMessage(NewBooking booking) {
		logger.info(">>> Received new Booking, Updating Inventory");
		//call repository and update the fare for the given flight
		searchService.updateInventory(booking.getFlightNumber(), booking.getFlightDate(), booking.getSeats());

	}
}
