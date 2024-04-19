package erp.pcpartsbackend.security;

import erp.pcpartsbackend.models.User;
import erp.pcpartsbackend.repositories.UserRepository;
import erp.pcpartsbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MyUserDetailService userDetailService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                        registry.requestMatchers("/register/**").permitAll();
                        registry.requestMatchers("/users/**").hasRole("ADMIN");
                        registry.requestMatchers("/products").permitAll();
                        registry.requestMatchers("/products/**").hasRole("ADMIN");
                        registry.requestMatchers("/products/**").hasRole("PROVIDER");
                        registry.requestMatchers("/orders/**").hasRole("ADMIN");
                        registry.requestMatchers("/orders/**").hasRole("CUSTOMER");
                        registry.requestMatchers("/productOrders").hasRole("CUSTOMER");
                        registry.requestMatchers("/customers/**").hasRole("CUSTOMER");
                        registry.requestMatchers("/providers/**").hasRole("PROVIDER");
                        registry.requestMatchers("/admins/**").hasRole("ADMIN");
                        registry.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults());

        return http.build();
    }



    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(getEncoder());
        return daoAuthenticationProvider;
    }



    @Bean
    BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}
