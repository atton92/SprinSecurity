package com.example.config;

import jakarta.servlet.FilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    //COMFIGURACION1
 /*   @Bean
    public SecurityFilterChain filterChain(HttpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests()
                  .requestMatchers("/v1/index2").permitAll()
                  .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .build();
    }
  */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v1/index2").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin()
                .successHandler(handler()) //URL se va a ir despues de iniciar secion
                .permitAll()
                .and()
                .sessionManagement()
                   .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                   .invalidSessionUrl("/login")
                   .maximumSessions(1)
                   .expiredUrl("/login")
                .sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation()
                   .migrateSession()
                .and()
                .build();
    }
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    //Nos redirije a "index"
    public AuthenticationSuccessHandler handler(){
        return (((request, response, authentication) -> {
            response.sendRedirect("/v1/session");
        }));
    }

}
