package erp.pcpartsbackend.security.dto;

public record RegisterRequest(String email, String password, String role) {
}
