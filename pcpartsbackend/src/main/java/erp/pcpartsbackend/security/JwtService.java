package erp.pcpartsbackend.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import erp.pcpartsbackend.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.auth0.jwt.JWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET_KEY = "635266556A576E5A7234753778214125442A472D4B6150645367566B59703273";

    private JwtService() {
    }

    public static String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public static String generateToken(
            User user
    ) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().toString())
                .withClaim("id", user.getUserId().toString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(authToken);
            return true;
        } catch(JWTDecodeException e) {
            return false;
        }
    }

}