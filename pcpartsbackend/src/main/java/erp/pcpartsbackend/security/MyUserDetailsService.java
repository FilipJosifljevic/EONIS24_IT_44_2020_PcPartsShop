package erp.pcpartsbackend.security;

import erp.pcpartsbackend.models.User;
import erp.pcpartsbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailContainingIgnoreCase(username);
        if(user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .email(userObj.getEmail())
                    .password(userObj.getPassword())
                    .role(userObj.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
