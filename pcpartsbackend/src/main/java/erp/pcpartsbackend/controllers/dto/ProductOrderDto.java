package erp.pcpartsbackend.controllers.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductOrderDto {
    private UUID productOrderId;
    private int quantity;
    private UUID orderId;
    private UUID productId;
}
