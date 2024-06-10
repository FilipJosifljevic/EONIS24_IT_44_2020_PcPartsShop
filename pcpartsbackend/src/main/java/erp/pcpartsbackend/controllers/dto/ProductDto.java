package erp.pcpartsbackend.controllers.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class ProductDto {
    private UUID productId;
    private String productName;
    private String productCategory;
    private float productPrice;
    private int quantityInStock;
    private String imageUrl;
    private UUID providerId;
}
