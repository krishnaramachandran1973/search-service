package com.cts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.domain.Flight;
import com.cts.service.SearchService;
import com.cts.vo.SearchQuery;

import lombok.RequiredArgsConstructor;

@RefreshScope
@RequiredArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	private final SearchService searchService;

	@Value("${originairports.shutdown}")
	String originShutDown;

	@PostMapping
	public ResponseEntity<?> search(@RequestBody SearchQuery query) {
		logger.info(">> Searching Flights with {}", query);
		if (query.getOrigin()
				.contains(originShutDown)) {
			return ResponseEntity.badRequest()
					.body("Airport is shutdown");
		}
		List<Flight> flights = searchService.search(query);
		return flights.size() > 0 ? ResponseEntity.ok(flights)
				: ResponseEntity.notFound()
						.build();
	}

}
