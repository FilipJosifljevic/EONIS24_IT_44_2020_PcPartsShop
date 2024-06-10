package erp.pcpartsbackend.stripe;

import erp.pcpartsbackend.models.ProductOrder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateChargeForm {
    private long price;
    private String description;
    private String token;
    private UUID orderId;
}
