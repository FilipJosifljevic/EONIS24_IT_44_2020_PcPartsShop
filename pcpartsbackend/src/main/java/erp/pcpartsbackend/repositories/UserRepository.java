package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailContainingIgnoreCase(String email);
    User findByUserId(UUID userId);
}
