package erp.pcpartsbackend.stripe;

import lombok.Data;

@Data
public class CreateChargeForm {
    private String token;
    private int price;
    private String currency;
    private String description;
}
