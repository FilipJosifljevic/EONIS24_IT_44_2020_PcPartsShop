package erp.pcpartsbackend.services;


import erp.pcpartsbackend.models.Provider;
import erp.pcpartsbackend.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Provider getProviderByProviderId(UUID providerId) {
        return providerRepository.findProviderByProviderId(providerId);
    }

    public Provider addProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public void deleteProvider(Provider provider) {
        providerRepository.delete(provider);
    }
}
