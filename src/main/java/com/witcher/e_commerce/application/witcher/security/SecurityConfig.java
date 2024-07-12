package com.witcher.e_commerce.application.witcher.security;

import com.witcher.e_commerce.application.witcher.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){

        DaoAuthenticationProvider auth= new DaoAuthenticationProvider();
        auth.setUserDetailsService((UserDetailsService) userService);
        auth.setPasswordEncoder(bCryptPasswordEncoder());

        return auth;
    }

    @Bean
    public WebSecurityCustomizer securityCustomizer(){
        return (web) ->
                web.ignoring().requestMatchers("/static/css/img/**","/style/**","/uploads/**","/templates");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(auth->
                        auth.requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                                form
                                        .loginPage("/login")
                                        .loginProcessingUrl("/authenticateTheUser")
                                        .usernameParameter("email")
                        .defaultSuccessUrl("/landing",true)


                )
                .logout(logout->
                        logout.permitAll()

                )
                .exceptionHandling(configurer->
                        configurer.accessDeniedPage("/access-denied")
                );
        return http.build();

    }


}
