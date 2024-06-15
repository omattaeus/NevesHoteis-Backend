package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDto(
        @NotBlank
        String password
) {
    public UserUpdateDto(User user){
        this( user.getPassword());
    }
}
