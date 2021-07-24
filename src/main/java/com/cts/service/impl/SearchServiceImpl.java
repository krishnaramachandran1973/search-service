package com.cts.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.domain.Flight;
import com.cts.domain.Inventory;
import com.cts.repository.FlightRepository;
import com.cts.service.SearchService;
import com.cts.vo.SearchQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SearchServiceImpl implements SearchService {
	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	private final FlightRepository flightRepository;

	@Override
	public void updateInventory(String flightNumber, LocalDate flightDate, int seats) {
		logger.info("Updating inventory for flight " + flightNumber + " inventory " + seats);
		Flight flight = flightRepository.findByFlightNumberAndFlightDate(flightNumber, flightDate);
		Inventory inv = flight.getInventory();
		inv.setSeats(seats);
		flightRepository.save(flight);

	}

	@Override
	public List<Flight> search(SearchQuery query) {
		logger.info("Querying for flights with {}", query);
		List<Flight> flights = flightRepository.findByOriginAndDestinationAndFlightDate(query.getOrigin(),
				query.getDestination(), query.getFlightDate());
		return flights.stream()
				.filter(flight -> flight.getInventory()
						.getSeats() > 0)
				.collect(Collectors.toList());
	}

}
