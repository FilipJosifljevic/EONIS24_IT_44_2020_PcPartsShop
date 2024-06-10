package erp.pcpartsbackend.security;

import erp.pcpartsbackend.models.Role;
import erp.pcpartsbackend.models.User;
import erp.pcpartsbackend.repositories.UserRepository;
import erp.pcpartsbackend.security.dto.AuthenticationRequest;
import erp.pcpartsbackend.security.dto.AuthenticationResponse;
import erp.pcpartsbackend.security.dto.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record AuthenticationService(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager) {
    public AuthenticationResponse register(RegisterRequest request) {
        final var user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                checkForRole(request.role()));
        userRepository.save(user);
        final var token = JwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final var user = userRepository.findByEmailContainingIgnoreCase(request.email()).orElseThrow();
        final var token = JwtService.generateToken(user);
        return new AuthenticationResponse(token);

    }

    public Role checkForRole(String role){
        Role newRole = null;
        if (role.equals("CUSTOMER")){
            newRole = Role.CUSTOMER;
        } else if (role.equals("ADMIN")){
            newRole =  Role.ADMIN;
        } else if (role.equals("PROVIDER")){
            newRole =  Role.PROVIDER;
        }
        return newRole;
    }
}
