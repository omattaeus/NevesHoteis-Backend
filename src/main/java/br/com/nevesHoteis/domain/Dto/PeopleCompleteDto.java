package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Date;

public record PeopleCompleteDto (
        Long id,
        String name,
        LocalDate birthDay,
        String cpf,
        String phone,
        AddressCompleteDto address,
        UserDto user
){
    public PeopleCompleteDto(People people){
        this(people.getId(), people.getName(), people.getBirthDay(), people.getCpf(),
                people.getPhone(), new AddressCompleteDto(people.getAddress()), new UserDto(people.getUser()));
    }
}
