package com.airline.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder (toBuilder = true)

public class OrderInfo {

    private String id;
    private String userId;
    private String orderPrice;
    private String orderStatus;
    private List<String> orderItems;

}
