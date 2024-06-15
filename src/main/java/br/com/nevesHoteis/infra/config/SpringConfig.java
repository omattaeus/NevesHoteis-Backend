package br.com.nevesHoteis.infra.config;

import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.infra.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Security;
import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class SpringConfig {
    @Autowired
    private AuthenticationFilter authenticationFilter;
    @Bean
    SecurityFilterChain config (HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(ses-> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req->{
                    req.requestMatchers(HttpMethod.GET, "/hotel/**").permitAll();
                    req.requestMatchers("/login").permitAll();
                    req.anyRequest().permitAll();
                })
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
    @Bean
    static RoleHierarchy roleHierarchy() {
        return null;
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
      return  configuration.getAuthenticationManager();
    }


}
