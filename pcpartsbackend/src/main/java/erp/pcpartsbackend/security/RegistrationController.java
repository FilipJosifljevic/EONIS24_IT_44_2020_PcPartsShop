package erp.pcpartsbackend.security;

import erp.pcpartsbackend.security.dto.AuthenticationRequest;
import erp.pcpartsbackend.security.dto.AuthenticationResponse;
import erp.pcpartsbackend.security.dto.RegisterRequest;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public record RegistrationController(AuthenticationService authenticationService) {
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
