package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService service;
    @Mock
    private EmployeeRepository repository;
    @Mock
    private Employee employeeMock;
    @Mock
    private Pageable pageable;
    @Mock
    private List<ValidateUser> validateUsers;
    @Mock
    private List<ValidatePeople> validatePeople;
    @DisplayName("Testando a seleção de todos os employees")
    @Test
    void test01(){
        Employee employee= randomEmployee();
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(page ,service.findAll(pageable));
        then(repository).should().findAll(any(Pageable.class));
    }
    @DisplayName("Testando a seleção de um employee")
    @Test
    void test02(){
        Employee employee= randomEmployee();
        when(repository.findById(anyLong())).thenReturn(Optional.of(employee));
        assertEquals(employee ,service.findById(employee.getId()));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando erro ao não achar um employee")
    @Test
    void test03(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> service.findById(1L));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando o salvamento da entidade employee")
    @Test
    void test04(){
        Employee employee = randomEmployee();
        when(repository.save(any())).thenReturn(employeeMock);
        assertEquals(employeeMock, service.save( new PeopleDto(employee)));
        then(repository).should().save(any());
        then(employeeMock).should().passwordEncoder();
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());
    }
    @DisplayName("Testando a atualização da entidade employee")
    @Test
    void test05(){
        Employee employee = randomEmployee();
        when(repository.findById(anyLong())).thenReturn(Optional.of(employeeMock));
        when(employeeMock.merge(any())).thenReturn(employee);
        assertEquals(employee, service.update(employee.getId(),  new PeopleUpdateDto(employee)));
        then(employeeMock).should().merge(any());
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());
    }
    @DisplayName("Testando a exclusão da entidade employee")
    @Test
    void test06(){
        Employee employee = randomEmployee();
        when(repository.findById(anyLong())).thenReturn(Optional.of(employeeMock));
        service.delete(anyLong());
        then(repository).should().delete(any());
    }
    public Employee randomEmployee() {
        return new Employee(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73 988888888", randomAddress(), randomUser());
    }

    public Address randomAddress(){
        return new Address(1L, "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }

    public User randomUser(){
        return new User(1L, "artur@gmail.com", "123", Role.EMPLOYEE);
    }
}