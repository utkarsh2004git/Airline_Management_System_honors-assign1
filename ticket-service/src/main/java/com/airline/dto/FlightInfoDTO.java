package com.airline.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class FlightInfoDTO {
    private String flightId;
    private String flightNumber;
    private String source;
    private String destination;
    private int availableSeats;
    private ScheduleInfoDTO schedule;
}
