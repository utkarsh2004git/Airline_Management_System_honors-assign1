package com.airline.controllers;

import com.airline.dto.FlightInfoDTO;
import com.airline.dto.ScheduleInfoDTO;
import com.airline.repository.FlightRepository;
import com.airline.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightRepository flightRepository;

    @Autowired
    public FlightController(FlightService flightService, FlightRepository flightRepository) {
        this.flightService = flightService;
        this.flightRepository = flightRepository;
    }

    @PostMapping
    public FlightInfoDTO createFlight(@RequestBody FlightInfoDTO flight) {

        // No need to parse the departureTime and arrivalTime if they are already LocalDateTime
        LocalDateTime depTime = flight.getSchedule().getDepartureTime();
        LocalDateTime arrTime = flight.getSchedule().getArrivalTime();

        // Create the flight with schedule
        return flightService.createFlight(
                flight.getFlightNumber(),
                flight.getSource(),
                flight.getDestination(),
                flight.getAvailableSeats(),
                depTime,
                arrTime
        );
    }

    @GetMapping
    public List<FlightInfoDTO> getAllFlights(@RequestParam(required = false) String sort) {
        List<FlightInfoDTO> flights = flightService.getAllFlights();

        if ("asc".equalsIgnoreCase(sort)) {
            flights.sort(Comparator.comparing(FlightInfoDTO::getFlightNumber));
        } else if ("desc".equalsIgnoreCase(sort)) {
            flights.sort(Comparator.comparing(FlightInfoDTO::getFlightNumber).reversed());
        }
        return flights;
    }


    @GetMapping("/{id}")
    public FlightInfoDTO getFlight(@PathVariable String id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/{id}/schedule")
    public List<ScheduleInfoDTO> getFlightSchedulesByDate(
            @PathVariable String id,
            @RequestParam(required = false) String dates) {

        FlightInfoDTO flight = flightService.getFlightById(id);

        System.out.println("Controller "+dates);

       return flightService.getFlightSchedulesByDate(flight,dates);
    }
}
