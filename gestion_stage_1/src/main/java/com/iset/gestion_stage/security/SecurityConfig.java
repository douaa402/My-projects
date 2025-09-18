package com.iset.gestion_stage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    // Après un login réussi
                    authentication.getAuthorities().forEach(grantedAuthority -> {
                        String role = grantedAuthority.getAuthority();
                        try {
                            if (role.equals("ROLE_ETUDIANT")) {
                                response.sendRedirect("/etudiant/offres");
                            } else if (role.equals("ROLE_ENTREPRISE")) {
                                response.sendRedirect("/entreprise/dashboard");
                            } else {
                                response.sendRedirect("/"); // par défaut
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                })
                .permitAll()
            )
            .logout(logout -> logout
    .logoutRequestMatcher(new OrRequestMatcher(
        new AntPathRequestMatcher("/etudiant/logout", "GET"),
        new AntPathRequestMatcher("/entreprise/logout", "GET")
    ))
    .logoutSuccessUrl("/login?logout")
    .invalidateHttpSession(true)
    .clearAuthentication(true)
    .permitAll()
)
        .userDetailsService(userDetailsService);
    
        return http.build();
    }
    


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

