package com.airline.services;

import com.airline.dto.FlightInfoDTO;
import com.airline.dto.ScheduleInfoDTO;
import com.airline.dto.TicketInfoDTO;

import com.airline.dto.UserInfo;
import com.airline.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${flight.service.url}")
    private String flightServiceUrl;

    public List<TicketInfoDTO> getAllTickets(){
        return ticketRepository.getAllTickets();
    }

    public Object createTicket(TicketInfoDTO ticketInfo){
        TicketInfoDTO createdTicketInfo =  ticketRepository.createTicket(ticketInfo);

        //update user-ticket-list
        String url = userServiceUrl+"/user-mgmt/users/"+ticketInfo.getUserId()+"/tickets/"+createdTicketInfo.getTicketId();
        restTemplate.put(url, null);

        //get userInfo
        String getUserurl =userServiceUrl+"/user-mgmt/users/"+ticketInfo.getUserId();
        UserInfo userInfo = restTemplate.getForObject(getUserurl, UserInfo.class);

        //get flightInfo
        String getFlighturl =flightServiceUrl+"/flight-mgmt/flights/"+ticketInfo.getFlightId();
        FlightInfoDTO flightInfo = restTemplate.getForObject(getFlighturl, FlightInfoDTO.class);


        return createdTicketInfo.toResponse(userInfo, flightInfo);
    }

    public Object getTicketById(String ticketId) throws Exception{
        TicketInfoDTO ticketInfo = ticketRepository.getTicketById(ticketId);

        if(ticketInfo == null){
            throw new Exception("order not found");
        }

        //get userInfo
        String getUserurl =userServiceUrl+"/user-mgmt/users/"+ticketInfo.getUserId();
        UserInfo userInfo = restTemplate.getForObject(getUserurl, UserInfo.class);

        //get flightInfo
        String getFlighturl =flightServiceUrl+"/flight-mgmt/flights/"+ticketInfo.getFlightId();
        FlightInfoDTO flightInfo = restTemplate.getForObject(getFlighturl, FlightInfoDTO.class);

        return ticketInfo.toResponse(userInfo,flightInfo);
    }

    public boolean deleteTicket(String ticketId){

        TicketInfoDTO ticketInfo = ticketRepository.getTicketById(ticketId);
        if(ticketInfo == null){
            return false;
        }
        String userId = ticketInfo.getUserId();

        String getUserurl =userServiceUrl+"/user-mgmt/users/"+userId;
        UserInfo userInfo = restTemplate.getForObject(getUserurl, UserInfo.class);

        if(userInfo == null){
            return false;
        }

        boolean deleted = ticketRepository.deleteTicket(ticketId);

        if(deleted){
            String deleteUserTicketUrl =userServiceUrl+"/user-mgmt/users/"+userId+"/tickets/"+ticketId;
            restTemplate.delete(deleteUserTicketUrl);
            return true;
        }
        return deleted;
    }

}
