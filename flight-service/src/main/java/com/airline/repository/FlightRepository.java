package com.airline.repository;

import com.airline.dto.FlightInfoDTO;
import com.airline.dto.ScheduleInfoDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FlightRepository {

    HashMap<String, FlightInfoDTO> flightInfotable = new HashMap<>();

    public HashMap<String,FlightInfoDTO>  demoFlights(HashMap<String,FlightInfoDTO> flightInfotable){
        ScheduleInfoDTO schedule1 = ScheduleInfoDTO.builder()
                .scheduleId("S1")
                .departureTime(LocalDateTime.of(2025, 3, 16, 10, 0)) // 2025-03-16 10:00
                .arrivalTime(LocalDateTime.of(2025, 3, 16, 12, 0)) // 2025-03-16 12:00
                .build();

        FlightInfoDTO flight1 = FlightInfoDTO.builder()
                .flightId("F1")
                .flightNumber("AI101")
                .source("Delhi")
                .destination("Mumbai")
                .availableSeats(100)
                .build();

        flightInfotable.put(flight1.getFlightId(), flight1.toBuilder().schedule(schedule1).build());

        ScheduleInfoDTO schedule2 = ScheduleInfoDTO.builder()
                .scheduleId("S2")
                .departureTime(LocalDateTime.of(2025, 3, 17, 14, 30)) // 2025-03-17 14:30
                .arrivalTime(LocalDateTime.of(2025, 3, 17, 16, 30)) // 2025-03-17 16:30
                .build();

        FlightInfoDTO flight2 = FlightInfoDTO.builder()
                .flightId("F2")
                .flightNumber("AI202")
                .source("Mumbai")
                .destination("Bangalore")
                .availableSeats(120)
                .build();

        flightInfotable.put(flight2.getFlightId(), flight2.toBuilder().schedule(schedule2).build());

        ScheduleInfoDTO schedule3 = ScheduleInfoDTO.builder()
                .scheduleId("S3")
                .departureTime(LocalDateTime.of(2025, 3, 18, 9, 0)) // 2025-03-18 09:00
                .arrivalTime(LocalDateTime.of(2025, 3, 18, 11, 30)) // 2025-03-18 11:30
                .build();

        FlightInfoDTO flight3 = FlightInfoDTO.builder()
                .flightId("F3")
                .flightNumber("AI303")
                .source("Bangalore")
                .destination("Chennai")
                .availableSeats(80)
                .build();

        flightInfotable.put(flight3.getFlightId(), flight3.toBuilder().schedule(schedule3).build());

        return flightInfotable;
    }


    @PostConstruct
     public void init() {
        flightInfotable = new HashMap<>();

        // Adding demo flights with schedule information

        flightInfotable = demoFlights(flightInfotable);


    }

    public List<FlightInfoDTO> getAllFlights(){
        return flightInfotable.values()
                .stream().collect(Collectors.toList());
    }

    public FlightInfoDTO createFlight(FlightInfoDTO flightInfoDTO, ScheduleInfoDTO scheduleInfoDTO) {
        String flightId = UUID.randomUUID().toString();
        FlightInfoDTO updatedFlight = flightInfoDTO.toBuilder()
                .flightId(flightId)
                .schedule(scheduleInfoDTO)
                .build();
        this.flightInfotable.put(flightId, updatedFlight);
        return updatedFlight;
    }

    public FlightInfoDTO getFlightById(String flightId){
        return flightInfotable.get(flightId);
    }




}
