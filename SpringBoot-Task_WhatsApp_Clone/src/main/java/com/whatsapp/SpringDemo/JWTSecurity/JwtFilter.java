package com.whatsapp.SpringDemo.JWTSecurity;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.whatsapp.SpringDemo.Repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MyUserDetailService myUserDetailService;
	
	@Autowired
	ApplicationContext context;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);   // get user details

            // Validate token
            if (jwtService.validateToken(token, userDetails)) {

//                // Fetch the Account entity to check if the user is blocked
//                User user = userRepository.findByEmail(email);
//
//                // Check if the user is blocked
//                if (user != null && user.getIsBlocked()) {
//
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Block access to protected endpoints
//                    response.getWriter().write("Your account is blocked. Please contact support.");
//                    return;
//                }

                // If not blocked, authenticate the user
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Proceed to the next filter or endpoint
        filterChain.doFilter(request, response);
		
	}

}
