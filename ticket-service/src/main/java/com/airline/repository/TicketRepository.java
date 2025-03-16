package com.airline.repository;

import com.airline.dto.TicketInfoDTO;
import com.airline.dto.UserInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TicketRepository {

    private final RestTemplate restTemplate;
    Map<String, TicketInfoDTO> ticketInfoTable;

    public TicketRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @PostConstruct
    public void init() {
        ticketInfoTable = new HashMap<>();
    }


    public List<TicketInfoDTO> getAllTickets(){
        return ticketInfoTable.values()
                .stream().collect(Collectors.toList());
    }

    public TicketInfoDTO createTicket(TicketInfoDTO ticketInfo) {
        String ticketId = UUID.randomUUID().toString();
        TicketInfoDTO updatedTicket = ticketInfo.toBuilder().ticketId(ticketId).build();
        this.ticketInfoTable.put(ticketId, updatedTicket );
        return updatedTicket;
    }


    public TicketInfoDTO getTicketById(String ticketId){
        return ticketInfoTable.get(ticketId);
    }

}
