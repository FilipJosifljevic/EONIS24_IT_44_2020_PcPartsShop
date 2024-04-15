package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailContainingIgnoreCase(String userName);
    User findByUserId(UUID userId);
}
