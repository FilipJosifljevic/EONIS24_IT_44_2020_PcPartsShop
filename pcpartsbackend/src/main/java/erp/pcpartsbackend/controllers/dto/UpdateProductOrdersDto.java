package erp.pcpartsbackend.controllers.dto;

import erp.pcpartsbackend.models.ProductOrder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateProductOrdersDto {
    private List<ProductOrder> productOrders;
    private UUID orderId;
}
