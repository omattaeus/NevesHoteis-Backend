package br.com.nevesHoteis.infra.filter;

import br.com.nevesHoteis.service.TokenService;
import br.com.nevesHoteis.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        if(token!=null) {
            String subject = tokenService.verify(token);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UserDetails userDetails = userService.loadUserByUsername(subject);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        doFilter(request, response, filterChain);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        return (token!= null ? token.contains("Bearer ") ? token.replace("Bearer ", "") : token  : null);
    }

}
