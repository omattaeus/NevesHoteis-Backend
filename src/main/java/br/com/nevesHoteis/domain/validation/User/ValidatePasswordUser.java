package br.com.nevesHoteis.domain.validation.User;

import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class ValidatePasswordUser implements ValidateUser{
    @Override
    public void validate(User user) {
    if(!strongPassword(user.getPassword())){
        throw new ValidateUserException("Password", "\n" +
                "The password must have one lowercase letter, one uppercase letter and one digit");
    }
    }
    //gambiarra provisória, depois refazer com regex
    public boolean strongPassword(String senha) {
        boolean findDigit = false;
        boolean findUpper = false;
        boolean findLetter = false;
        for (char c : senha.toCharArray()) {
            if (c >= '0' && c <= '9') {
                findDigit = true;
            } else if (c >= 'A' && c <= 'Z') {
                findUpper = true;
            } else if (c >= 'a' && c <= 'z') {
                findLetter = true;
            }
        }
        return findDigit && findUpper && findLetter ;
    }


}
