package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
    ProductOrder findByProductId(UUID productId);
}
