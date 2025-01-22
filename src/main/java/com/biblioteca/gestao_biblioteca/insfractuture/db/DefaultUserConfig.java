package com.biblioteca.gestao_biblioteca.insfractuture.db;

import com.biblioteca.gestao_biblioteca.enums.Papel;
import com.biblioteca.gestao_biblioteca.models.Auth;
import com.biblioteca.gestao_biblioteca.repository.AuthRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultUserConfig {

    @Bean
    public CommandLineRunner initDefaultUser(AuthRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin@gmail.com")) {
                Auth user = new Auth();
                user.setUsername("admin@gmail.com");
                String encriptedPassword = new BCryptPasswordEncoder().encode("netline");
                user.setPassword(encriptedPassword);
                user.setRole(Papel.ADMIN);
                userRepository.save(user);
            }
        };
    }
}
