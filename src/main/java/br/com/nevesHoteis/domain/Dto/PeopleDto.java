package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public record PeopleDto(
        @NotBlank
        @Size(min = 3, max = 60)
        String name,
        @NotNull
        @Past
        LocalDate birthDay,
        @NotBlank
        @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}-\\d{2}")
        String cpf,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String phone,
        @NotNull
        @Valid
        AddressDto address,
        @NotNull
        @Valid
        LoginDto user
) {
    public PeopleDto(People people){
        this(people.getName(), people.getBirthDay(), people.getCpf(),
             people.getPhone(), new AddressDto(people.getAddress()), new LoginDto(people.getUser()));
    }
}
