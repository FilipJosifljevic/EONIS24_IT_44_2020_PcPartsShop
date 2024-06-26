package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Admin findByUserId(UUID adminId);
}
