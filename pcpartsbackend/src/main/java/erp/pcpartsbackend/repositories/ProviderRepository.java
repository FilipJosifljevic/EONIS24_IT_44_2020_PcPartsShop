package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    Provider findProviderByUserId(UUID providerId);
}
