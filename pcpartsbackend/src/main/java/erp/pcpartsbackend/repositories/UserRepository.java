package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserNameContainingIgnoreCase(String userName);
    User findByUserId(UUID userId);
}
