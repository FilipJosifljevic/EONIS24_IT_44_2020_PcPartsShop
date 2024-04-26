package erp.pcpartsbackend.security;

import erp.pcpartsbackend.models.User;
import erp.pcpartsbackend.repositories.UserRepository;
import erp.pcpartsbackend.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
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
                        registry.requestMatchers(HttpMethod.GET, "/products/**").permitAll();
                        registry.requestMatchers(HttpMethod.PUT, "/products/**").hasAnyRole("PROVIDER", "ADMIN");
                        registry.requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyRole("PROVIDER", "ADMIN");
                        registry.requestMatchers(HttpMethod.POST,"/products/**").hasRole("PROVIDER");
                        registry.requestMatchers("/orders/**").hasAnyRole("ADMIN", "CUSTOMER");// customer samo svoje ordere na backendu
                        registry.requestMatchers("/productOrders").hasRole("CUSTOMER");
                        registry.requestMatchers("/customers/**").hasAnyRole("ADMIN", "CUSTOMER");
                        registry.requestMatchers(HttpMethod.GET, "/providers/**").permitAll();
                        registry.requestMatchers(HttpMethod.POST, "/providers/**").hasRole("PROVIDER");
                        registry.requestMatchers(HttpMethod.PUT, "/providers/**").hasRole("PROVIDER");
                        registry.requestMatchers(HttpMethod.DELETE, "/providers/**").hasAnyRole("ADMIN", "PROVIDER");
                        registry.requestMatchers("/admins/**").hasRole("ADMIN");
                        registry.requestMatchers(HttpMethod.OPTIONS).permitAll();
                        registry.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                //.formLogin(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider());
                //.exceptionHandling((exception) -> exception.accessDeniedHandler(forbiddenHandler()).authenticationEntryPoint(unauthorizedEntryPoint()))

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
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        };
    }

    @Bean
    public AccessDeniedHandler forbiddenHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        };
    }

    @Bean
    BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}
