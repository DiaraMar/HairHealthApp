package com.kamikarow.hairCareProject.config;

import com.kamikarow.hairCareProject.infra.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class hold all the application configurations
 * @Configuration forced Spring at every start up of the application to pick up configurations and implements all beans within the classes
 * N.B. Beans are always public methods
 * @RequiredArgsConstructor to inject final variables stated
 */

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserJpaRepository userJPARepository;

    /**
     * Get user from database
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userJPARepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found")); //since our repositorymethod is of type optional with have to manage the case the username is not found
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}

