package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        @Email
        String login,
        @NotBlank
        String password,
        @NotNull
        Role role
) {
    public UserDto(User user){
        this(user.getLogin(), user.getPassword(), user.getRole());
    }
}
