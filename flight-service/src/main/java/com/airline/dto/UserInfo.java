package com.airline.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder (toBuilder = true)

public class UserInfo {

    private String id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private List<String> bookedTicketIds;


}
