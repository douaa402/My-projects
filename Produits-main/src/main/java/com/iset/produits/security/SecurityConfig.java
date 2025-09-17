package com.iset.produits.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN")
                .build();

        UserDetails najla = User.withUsername("Najla")
                .password(passwordEncoder.encode("123"))
                .roles("AGENT", "USER")
                .build();

        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder.encode("123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, najla, user1);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/showCreate", "/saveProduit").hasAnyRole("ADMIN", "AGENT")
                .requestMatchers("/ListeProduits").hasAnyRole("ADMIN", "AGENT", "USER")
                .requestMatchers("/supprimerProduit", "/modifierProduit", "/updateProduit").hasRole("ADMIN")
                .anyRequest().authenticated()
        )
        .formLogin()
        .and()
        .exceptionHandling().accessDeniedPage("/accessDenied");

        return http.build();
    }
}
