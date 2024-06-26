package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>{

    Customer findByUserId(UUID customerId);
}
