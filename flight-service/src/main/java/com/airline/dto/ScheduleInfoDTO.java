package com.airline.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class ScheduleInfoDTO {
    private String scheduleId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}
