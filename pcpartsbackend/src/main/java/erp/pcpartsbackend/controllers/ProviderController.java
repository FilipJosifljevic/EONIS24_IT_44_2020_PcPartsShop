package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.models.Provider;
import erp.pcpartsbackend.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class ProviderController {
    
    @Autowired
    private ProviderService providerService;

    @GetMapping("providers")
    public ResponseEntity<?> getAllproviders() {
        List<Provider> providers = providerService.getAllProviders();
        if (providers.isEmpty()) {
            return new ResponseEntity<>(
                    "Providers not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping("providers/{id}")
    public ResponseEntity<?> getproviderById(@PathVariable("id") UUID providerId) {
        Provider provider = providerService.getProviderByProviderId(providerId);
        if (provider == null) {
            return new ResponseEntity<>(
                    "Provider not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(provider, HttpStatus.OK);
    }

    @PostMapping("providers")
    public ResponseEntity<?> addProvider(@RequestBody Provider provider) {
        if(providerService.existById(provider.getUserId())){
            return new ResponseEntity<>(
                    "Provider with that id already exists",
                    HttpStatus.CONFLICT);
        }
        Provider savedprovider = providerService.addProvider(provider);
        return ResponseEntity.status(HttpStatus.OK).body(savedprovider);
    }

    @PutMapping("providers/{id}")
    public ResponseEntity<?> updateProvider(@RequestBody Provider provider, @PathVariable("id") UUID providerId) {
        provider.setUserId(providerId);
        if(!providerService.existById(provider.getUserId())){
            return new ResponseEntity<>(
                    "Provider with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        Provider savedProvider = providerService.addProvider(provider);
        return ResponseEntity.status(HttpStatus.OK).body(savedProvider);
    }

    @DeleteMapping("providers/{id}")
    public ResponseEntity<String> deleteProvider(@PathVariable("id") UUID providerId) {
        if(!providerService.existById(providerId)){
            return new ResponseEntity<>(
                    "Provider with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        providerService.deleteProvider(providerService.getProviderByProviderId(providerId));
        return ResponseEntity.status(HttpStatus.OK).body("Provider with that id has been deleted");
    }
}
