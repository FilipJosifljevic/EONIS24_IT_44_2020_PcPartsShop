package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
