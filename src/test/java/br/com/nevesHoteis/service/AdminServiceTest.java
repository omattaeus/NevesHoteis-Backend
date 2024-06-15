package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.AdminRepository;
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
class AdminServiceTest {
    @InjectMocks
    private AdminService service;
    @Mock
    private AdminRepository repository;
    @Mock
    private Admin adminMock;
    @Mock
    private Pageable pageable;
    @Mock
    private List<ValidateUser> validateUsers;
    @Mock
    private List<ValidatePeople> validatePeople;
    @DisplayName("Testando a seleção de todos os admins")
    @Test
    void test01(){
        Admin admin= randomAdmin();
        Page<Admin> page = new PageImpl<>(List.of(admin));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(page ,service.findAll(pageable));
        then(repository).should().findAll(any(Pageable.class));
    }
    @DisplayName("Testando a seleção de um admin")
    @Test
    void test02(){
        Admin admin= randomAdmin();
        when(repository.findById(anyLong())).thenReturn(Optional.of(admin));
        assertEquals(admin ,service.findById(admin.getId()));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando erro ao não achar um admin")
    @Test
    void test03(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> service.findById(1L));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando o salvamento da entidade admin")
    @Test
    void test04(){
        Admin admin = randomAdmin();
        when(repository.save(any())).thenReturn(adminMock);
        assertEquals(adminMock, service.save( new PeopleDto(admin)));
        then(repository).should().save(any());
        then(adminMock).should().passwordEncoder();
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());
    }
    @DisplayName("Testando a atualização da entidade admin")
    @Test
    void test05(){
        Admin admin = randomAdmin();
        when(repository.findById(anyLong())).thenReturn(Optional.of(adminMock));
        when(adminMock.merge(any())).thenReturn(admin);
        assertEquals(admin, service.update(admin.getId(),  new PeopleUpdateDto(admin)));
        then(adminMock).should().merge(any());
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());
    }
    @DisplayName("Testando a exclusão da entidade admin")
    @Test
    void test06(){
        Admin admin = randomAdmin();
        when(repository.findById(anyLong())).thenReturn(Optional.of(adminMock));
        service.delete(anyLong());
        then(repository).should().delete(any());
    }
    public Admin randomAdmin() {
        return new Admin(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73 988888888", randomAddress(), randomUser());
    }

    public Address randomAddress(){
        return new Address(1L, "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }

    public User randomUser(){
        return new User(1L, "artur@gmail.com", "123", Role.ADMIN);
    }
}