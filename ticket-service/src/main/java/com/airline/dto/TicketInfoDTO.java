package com.airline.dto;
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

}
