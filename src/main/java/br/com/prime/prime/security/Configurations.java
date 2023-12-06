package br.com.prime.prime.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class Configurations {

    @Autowired
    private FilterToken filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/usuario/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuario/cadastro").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/register/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/register/reset-password/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/register/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuario/password-reset-request").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuario/reset-password").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/estabelecimento").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/estabelecimento/buscarEstabelecimentoPorNome").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/produto").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/produto/categoria").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/produto/buscarPorNome").permitAll();
                    req.requestMatchers(HttpMethod.GET, "http://localhost:3000").permitAll();
                    req.requestMatchers(HttpMethod.GET, "http://127.0.0.1:5501/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "http://127.0.0.1:5501/**").permitAll();
                    req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    } 

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
