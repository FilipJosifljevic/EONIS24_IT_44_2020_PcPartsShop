package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(UUID orderId);
    List<Order> findAllByCustomer_UserId(UUID customerId);
    List<Order> findAllByOrderStatusIgnoreCase(String status);
    List<Order> findAllByOrderDate(Date orderDate);

}
