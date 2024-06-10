package erp.pcpartsbackend.security;

import erp.pcpartsbackend.models.Role;
import erp.pcpartsbackend.models.User;
import erp.pcpartsbackend.repositories.UserRepository;
import erp.pcpartsbackend.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Order;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                        registry.requestMatchers("/register/**").permitAll();
                        registry.requestMatchers("/login/**").permitAll();
                        registry.requestMatchers("/charges").hasAnyAuthority("ADMIN", "CUSTOMER");
                        registry.requestMatchers("/webhook").permitAll();
                        registry.requestMatchers( "/users/**").permitAll();
                        //registry.requestMatchers("/users/**").hasRole("ADMIN");
                        registry.requestMatchers(HttpMethod.GET, "/products/**").permitAll();
                        registry.requestMatchers(HttpMethod.PUT, "/products/**").hasAnyAuthority("PROVIDER", "ADMIN");
                        registry.requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyAuthority("ADMIN", "PROVIDER");
                        registry.requestMatchers(HttpMethod.POST,"/products/**").hasAuthority("PROVIDER");
                        registry.requestMatchers("/orders/**").hasAnyAuthority("ADMIN", "CUSTOMER");
                        registry.requestMatchers("/productOrders/**").permitAll();
                        registry.requestMatchers("/customers/**").hasAnyAuthority("ADMIN", "CUSTOMER", "PROVIDER");
                        registry.requestMatchers(HttpMethod.GET, "/providers/**").permitAll();
                        registry.requestMatchers(HttpMethod.POST, "/providers/**").hasAuthority("PROVIDER");
                        registry.requestMatchers(HttpMethod.PUT, "/providers/**").hasAuthority("PROVIDER");
                        registry.requestMatchers(HttpMethod.DELETE, "/providers/**").hasAnyAuthority("ADMIN", "PROVIDER");
                        registry.requestMatchers("/admins/**").hasAuthority("ADMIN");
                        registry.requestMatchers(HttpMethod.OPTIONS).permitAll();
                        registry.anyRequest().authenticated();
                })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
