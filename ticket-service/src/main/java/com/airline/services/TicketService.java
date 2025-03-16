package com.airline.services;

import com.airline.dto.TicketInfoDTO;

import com.airline.dto.UserInfo;
import com.airline.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private RestTemplate restTemplate;


    public List<TicketInfoDTO> getAllTickets(){
        return ticketRepository.getAllTickets();
    }

    public Object createTicket(TicketInfoDTO ticketInfo){
        TicketInfoDTO createdTicketInfo =  ticketRepository.createTicket(ticketInfo);

        //update user-ticket-list
        String url = "http://localhost:5000/user-mgmt/users/"+ticketInfo.getUserId()+"/"+createdTicketInfo.getTicketId() ;
        restTemplate.put(url, null);

        String getUserurl ="http://localhost:5000/user-mgmt/users/"+ticketInfo.getUserId();
        UserInfo userInfo = restTemplate.getForObject(getUserurl, UserInfo.class);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        // Add ticket details
        response.put("ticketId", createdTicketInfo.getTicketId());
        response.put("userId", createdTicketInfo.getUserId());
        response.put("flightId", createdTicketInfo.getFlightId());
        response.put("seatNumber", createdTicketInfo.getSeatNumber());
        response.put("price", createdTicketInfo.getPrice());
        response.put("status", createdTicketInfo.getStatus());
        response.put("bookingTime", createdTicketInfo.getBookingTime().toString());
        // Add user details
        response.put("customerName", userInfo.getName());
        response.put("customerEmail", userInfo.getEmail());
        response.put("customerAddress", userInfo.getAddress());
        response.put("customerPhone", userInfo.getPhone());


        return response;
    }

    public TicketInfoDTO getTicketById(String ticketId) throws Exception{
        TicketInfoDTO ticketInfo = ticketRepository.getTicketById(ticketId);
        if(ticketInfo == null){
            throw new Exception("order not found");
        }
        return ticketInfo;
    }

}
