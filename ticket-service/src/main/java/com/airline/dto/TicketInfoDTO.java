package com.airline.dto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder (toBuilder = true)

public class TicketInfoDTO {

    private String ticketId;
    private String userId;
    private String flightId;
    private String seatNumber;
    private double price;
    private String status;
    private LocalDateTime bookingTime;

    public ObjectNode toResponse(UserInfo userInfo, FlightInfoDTO flightInfo) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();

        // Add ticket details
        response.put("ticketId", ticketId);
        response.put("userId", userId);
        response.put("flightId", flightId);
        response.put("seatNumber", seatNumber);
        response.put("price", price);
        response.put("status", status);
        response.put("bookingTime", bookingTime.toString());

        // Add user details (if available)
        if (userInfo != null) {
            response.put("customerName", userInfo.getName());
            response.put("customerEmail", userInfo.getEmail());
            response.put("customerAddress", userInfo.getAddress());
            response.put("customerPhone", userInfo.getPhone());
        }

        // Add flight details (if available)
        if (flightInfo != null && flightInfo.getSchedule() != null) {
            response.put("flightNumber", flightInfo.getFlightNumber());
            response.put("SOURCE", flightInfo.getSource());
            response.put("DESTINATION", flightInfo.getDestination());
            response.put("departureTime", flightInfo.getSchedule().getDepartureTime().toString());
            response.put("arrivalTime", flightInfo.getSchedule().getArrivalTime().toString());
        }

        return response;
    }
}
