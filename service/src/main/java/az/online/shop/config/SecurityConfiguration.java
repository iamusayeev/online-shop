package az.online.shop.config;

import static az.online.shop.model.Role.ADMIN;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        urlConfig -> urlConfig
                                .antMatchers("/users/registration", "/login", "v3/api-docs/**", "swagger-ui/**").permitAll()
                                .antMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                                .anyRequest().authenticated()

                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users"));
        return http.build();
    }
}