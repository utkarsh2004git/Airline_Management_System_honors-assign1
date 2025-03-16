package com.airline.services;

import com.airline.dto.TicketInfoDTO;

import com.airline.repository.TicketRepository;
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

    public TicketInfoDTO createTicket(TicketInfoDTO ticketInfo){
        TicketInfoDTO createdTicketInfo =  ticketRepository.createTicket(ticketInfo);

//        UserInfo userInfo = restTemplate.getForObject("http://localhost:9055/user-mgmt/users/"+orderInfo.getUserId(), UserInfo.class);

//        String url = "http://localhost:9055//user-mgmt//users//"+orderInfo.getUserId()+"//"+ TicketInfoDTO.getId();
//        System.out.println(url);
//        restTemplate.put(url, null);

        return createdTicketInfo;

    }

    public TicketInfoDTO getTicketById(String ticketId) throws Exception{
        TicketInfoDTO ticketInfo = ticketRepository.getTicketById(ticketId);
        if(ticketInfo == null){
            throw new Exception("order not found");
        }
        return ticketInfo;
    }

}
