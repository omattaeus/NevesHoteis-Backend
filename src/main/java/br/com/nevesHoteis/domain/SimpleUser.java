package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.domain.Dto.PeopleCompleteDto;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity()
@Getter

public class SimpleUser extends People {
    public SimpleUser(Long id, String name, LocalDate birthDay, String cpf, String phone, Address address, User user) {
        super(id, name, birthDay, cpf, phone, address, user);
        setRole(Role.USER);
    }

    public SimpleUser() {

    }

    public SimpleUser(PeopleDto dto) {
        super(dto);
        setRole(Role.USER);
    }
    public SimpleUser(PeopleUpdateDto dto){
        super(dto);
        setRole(Role.USER);
    }

}
