package br.com.nevesHoteis.domain.validation.People;

import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class ValidateBirthdayPeople implements ValidatePeople{
    @Override
    public void validate(People people) {

    if (!(people.getBirthDay().isBefore(LocalDate.now().plusYears(-14)) && people.getBirthDay().isAfter(LocalDate.now().plusYears(-130)))){
        throw new ValidateUserException("BirthDay", "Idade inválida");
    }
    }

}
