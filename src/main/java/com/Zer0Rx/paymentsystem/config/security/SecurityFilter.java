package com.Zer0Rx.paymentsystem.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Zer0Rx.paymentsystem.repositories.UserRepository;
import com.Zer0Rx.paymentsystem.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {
                var token = this.recoveryToken(request);
                if(token != null){
                    String subject = tokenService.validateToken(token);
                    UserDetails user = userRepository.findByEmail(subject);

                    var authentication = new UsernamePasswordAuthenticationToken(user, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(request, response);
            }
    
    private String recoveryToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
