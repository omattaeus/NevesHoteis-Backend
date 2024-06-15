package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Dto.TokenDto;
import br.com.nevesHoteis.domain.Dto.LoginDto;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @PostMapping()
    ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken userToken= new UsernamePasswordAuthenticationToken(loginDto.login(), loginDto.password());
        Authentication authentication =manager.authenticate(userToken);
        return ResponseEntity.ok(new TokenDto(tokenService.createdToken((User) authentication.getPrincipal())));
    }
}
