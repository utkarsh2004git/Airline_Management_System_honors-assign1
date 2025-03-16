package com.airline;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class TicketConfigurations {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
