package com.witcher.e_commerce.application.witcher.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("authentication status?" +authentication.getAuthorities());

        var authourities= authentication.getAuthorities();
         var roles= authourities.stream().map(r -> r.getAuthority()).findFirst();

         if (roles.orElse(" ").equals("ROLE_ADMIN")){
             response.sendRedirect("/admin/dashboard");
         } else if (roles.orElse(" ").equals("ROLE_USER")) {
             response.sendRedirect("/landing");
         }else{
             response.sendRedirect("/access-denied");
         }

    }
}
