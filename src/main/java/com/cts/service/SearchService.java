package com.cts.service;

import java.time.LocalDate;
import java.util.List;

import com.cts.domain.Flight;
import com.cts.vo.SearchQuery;

public interface SearchService {
	List<Flight> search(SearchQuery query);

	void updateInventory(String flightNumber, LocalDate flightDate, int inventory);
}
