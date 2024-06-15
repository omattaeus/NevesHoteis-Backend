package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity

public class Employee extends People{
    public Employee(Long id, String name, LocalDate birthDay, String cpf, String phone, Address address, User user) {
        super(id, name, birthDay, cpf, phone, address, user);
        setRole(Role.EMPLOYEE);
    }

    public Employee() {

    }
    public Employee(PeopleDto dto) {
        super(dto);
        setRole(Role.EMPLOYEE);
    }
    public Employee(PeopleUpdateDto dto){
        super(dto);
        setRole(Role.EMPLOYEE);
    }
}
