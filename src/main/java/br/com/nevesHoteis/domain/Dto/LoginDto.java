package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @Email
        String login,
        @NotBlank
        @Size(min = 8, max = 30)
        String password
) {
        public LoginDto(User user){
                this(user.getLogin(), user.getPassword());
        }

}
