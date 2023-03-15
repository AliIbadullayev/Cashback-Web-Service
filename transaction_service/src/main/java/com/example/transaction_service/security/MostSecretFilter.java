package com.example.transaction_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MostSecretFilter extends OncePerRequestFilter {

    @Value("${transaction.service.secret}")
    private String TRANSACTION_SECRET;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("MostSecretKey") == null || !request.getHeader("MostSecretKey").equals(TRANSACTION_SECRET)) {
            SecurityContextHolder.clearContext();
            throw new RuntimeException("You can't access this service!");
        }
        filterChain.doFilter(request, response);
    }
}
