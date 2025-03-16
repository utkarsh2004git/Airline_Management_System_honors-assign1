package com.airline.services;

import com.airline.dto.FlightInfoDTO;
import com.airline.dto.ScheduleInfoDTO;
import com.airline.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightInfoDTO createFlight(String flightNumber, String source, String destination, int availableSeats,
                                      LocalDateTime departureTime, LocalDateTime arrivalTime) {
        // Create the schedule information
        ScheduleInfoDTO schedule = ScheduleInfoDTO.builder()
                .scheduleId(UUID.randomUUID().toString())
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .build();

        // Create the flight information
        FlightInfoDTO flightInfo = FlightInfoDTO.builder()
                .flightNumber(flightNumber)
                .source(source)
                .destination(destination)
                .availableSeats(availableSeats)
                .build();

        // Create the flight and add schedule
        return flightRepository.createFlight(flightInfo, schedule);
    }

    public List<FlightInfoDTO> getAllFlights() {
        return flightRepository.getAllFlights();
    }

    public FlightInfoDTO getFlightById(String flightId) {
        return flightRepository.getFlightById(flightId);
    }


    public List<ScheduleInfoDTO> getFlightSchedulesByDate(FlightInfoDTO flight, String dates) {
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }

        // Get the schedule for the flight
        List<ScheduleInfoDTO> schedules = new ArrayList<>();
        schedules.add(flight.getSchedule());

        // If no dates parameter is provided, return all schedules
        if (dates == null || dates.isEmpty()) {
            return schedules;
        }

        // Split the dates if it's a range (e.g., "2025-03-15 to 2025-03-20")
        String[] dateRange = dates.split("to");

        // If it's a range, parse both dates
        if (dateRange.length == 2) {
            // Parse the start and end dates from the date range
            LocalDate startDate = LocalDate.parse(dateRange[0].trim());
            LocalDate endDate = LocalDate.parse(dateRange[1].trim());

            // Filter schedules that are within the given date range
            List<ScheduleInfoDTO> filteredSchedules = schedules.stream()
                    .filter(schedule -> {
                        LocalDate departureDate = schedule.getDepartureTime().toLocalDate();
                        // Check if the schedule's departure date is within the date range
                        return (departureDate.isEqual(startDate) || departureDate.isEqual(endDate) ||
                                (departureDate.isAfter(startDate) && departureDate.isBefore(endDate)));
                    })
                    .collect(Collectors.toList());

            // If no schedules found within the date range, return a 404 response
            if (filteredSchedules.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No schedules found in the given date range");
            }

            return filteredSchedules;
        }

        // If the dates are not in the correct range format, return a 400 response
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date range format");
    }


}
