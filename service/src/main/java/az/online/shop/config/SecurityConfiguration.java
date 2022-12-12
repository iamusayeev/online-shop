package az.online.shop.config;

import static az.online.shop.model.Role.ADMIN;
import static az.online.shop.model.Role.MANAGER;
import static az.online.shop.model.Role.USER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .antMatchers("/users").hasAuthority(ADMIN.getAuthority())
                        .antMatchers("users/{\\id}/delete").hasAuthority(ADMIN.getAuthority())
                        .antMatchers(HttpMethod.GET, "/orders/findAllByUser").hasAuthority(USER.getAuthority())
                        .antMatchers(HttpMethod.GET, "/orders").hasAuthority(ADMIN.getAuthority())
                        .antMatchers(HttpMethod.GET, "/orders/{\\id}").hasAuthority(ADMIN.getAuthority())
                        .antMatchers(HttpMethod.GET, "/categories").permitAll()
                        .antMatchers(HttpMethod.GET, "/products").permitAll()
                        .antMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                        .antMatchers(HttpMethod.POST, "/users").permitAll()
                        .antMatchers(HttpMethod.GET, "/users").hasAnyAuthority(ADMIN.getAuthority(), MANAGER.getAuthority())
                        .antMatchers("/users/{\\d+}/delete").hasAuthority(ADMIN.getAuthority())
                        .antMatchers(HttpMethod.POST, "/categories").hasAuthority(ADMIN.getAuthority())
                        .antMatchers(HttpMethod.POST, "/orders").hasAuthority(USER.getAuthority())
                        .antMatchers("/buckets").hasAuthority(USER.getAuthority())
                        .antMatchers("/api/v1/products/{\\id}/avatar").permitAll()
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/"));
        return http.build();
    }
}