package erp.pcpartsbackend.controllers.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID orderId;
    private Date orderDate;
    private Float orderPrice;
    private String orderStatus;
    private double discount;
    private String promoCode;
    private UUID customerId;
}
