package com.cts;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.cts.domain.Fare;
import com.cts.domain.Flight;
import com.cts.domain.Inventory;
import com.cts.repository.FlightRepository;
import com.cts.service.SearchService;
import com.cts.vo.SearchQuery;

import lombok.RequiredArgsConstructor;

@EnableDiscoveryClient
@RequiredArgsConstructor
@SpringBootApplication
public class SearchServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(SearchServiceApplication.class);

	private final FlightRepository flightRepository;
	private final SearchService searchService;

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			List<Flight> flights = Arrays.asList(Flight.builder()
					.flightNumber("BF100")
					.origin("SEA")
					.destination("SFO")
					.flightDate(LocalDate.of(2021, 8, 22))
					.fare(Fare.builder()
							.amount("100")
							.currency("INR")
							.build())
					.inventory(Inventory.builder()
							.seats(100)
							.build())
					.build(),

					Flight.builder()
							.flightNumber("BF101")
							.origin("NYC")
							.destination("SFO")
							.flightDate(LocalDate.of(2021, 9, 18))
							.fare(Fare.builder()
									.amount("101")
									.currency("INR")
									.build())
							.inventory(Inventory.builder()
									.seats(100)
									.build())
							.build(),

					Flight.builder()
							.flightNumber("BF102")
							.origin("CHI")
							.destination("SFO")
							.flightDate(LocalDate.of(2021, 10, 12))
							.fare(Fare.builder()
									.amount("102")
									.currency("INR")
									.build())
							.inventory(Inventory.builder()
									.seats(100)
									.build())
							.build(),

					Flight.builder()
							.flightNumber("BF103")
							.origin("HOU")
							.destination("SFO")
							.flightDate(LocalDate.of(2021, 11, 05))
							.fare(Fare.builder()
									.amount("103")
									.currency("INR")
									.build())
							.inventory(Inventory.builder()
									.seats(100)
									.build())
							.build(),

					Flight.builder()
							.flightNumber("BF104")
							.origin("LAX")
							.destination("SFO")
							.flightDate(LocalDate.of(2021, 12, 01))
							.fare(Fare.builder()
									.amount("104")
									.currency("INR")
									.build())
							.inventory(Inventory.builder()
									.seats(100)
									.build())
							.build(),

					Flight.builder()
							.flightNumber("BF105")
							.origin("NYC")
							.destination("SFO")
							.flightDate(LocalDate.of(2021, 8, 16))
							.fare(Fare.builder()
									.amount("105")
									.currency("INR")
									.build())
							.inventory(Inventory.builder()
									.seats(100)
									.build())
							.build(),

					Flight.builder()
							.flightNumber("BF106")
							.origin("NYC")
							.destination("SFO")
							.flightDate(LocalDate.of(2021, 8, 19))
							.fare(Fare.builder()
									.amount("106")
									.currency("INR")
									.build())
							.inventory(Inventory.builder()
									.seats(100)
									.build())
							.build());

			flightRepository.saveAll(flights);

			logger.info("Looking to load flights...");
			for (Flight flight : flightRepository.findByOriginAndDestinationAndFlightDate("SEA", "SFO",
					LocalDate.of(2021, 8, 22))) {
				logger.info(flight.toString());
			}

			logger.info("Looking to load flights with SearchService");
			searchService.search(SearchQuery.builder()
					.origin("SEA")
					.destination("SFO")
					.flightDate(LocalDate.of(2021, 8, 22))
					.build())
					.forEach(flight -> logger.info(flight.toString()));
		};
	}

}
