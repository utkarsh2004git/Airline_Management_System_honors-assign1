package com.airline.services;

import com.airline.dto.FlightInfoDTO;
import com.airline.dto.ScheduleInfoDTO;
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


        //get userInfo
        String getUserurl ="http://localhost:5000/user-mgmt/users/"+ticketInfo.getUserId();
        UserInfo userInfo = restTemplate.getForObject(getUserurl, UserInfo.class);

        //get flightInfo
        String getFlighturl ="http://localhost:5001/flight-mgmt/flights/"+ticketInfo.getFlightId();
        FlightInfoDTO flightInfo = restTemplate.getForObject(getFlighturl, FlightInfoDTO.class);


        return createdTicketInfo.toResponse(userInfo, flightInfo);
    }

    public Object getTicketById(String ticketId) throws Exception{
        TicketInfoDTO ticketInfo = ticketRepository.getTicketById(ticketId);

        if(ticketInfo == null){
            throw new Exception("order not found");
        }

        //get userInfo
        String getUserurl ="http://localhost:5000/user-mgmt/users/"+ticketInfo.getUserId();
        UserInfo userInfo = restTemplate.getForObject(getUserurl, UserInfo.class);

        //get flightInfo
        String getFlighturl ="http://localhost:5001/flight-mgmt/flights/"+ticketInfo.getFlightId();
        FlightInfoDTO flightInfo = restTemplate.getForObject(getFlighturl, FlightInfoDTO.class);

        return ticketInfo.toResponse(userInfo,flightInfo);
    }

}
