package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.SimpleUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SimpleUserServiceTest {
    @InjectMocks
    private SimpleUserService service;
    @Mock
    private SimpleUserService simpleUserService;
    @Mock
    private SimpleUserService simpleUserService2;
    @Mock
    private SimpleUserRepository repository;
    @Mock
    private SimpleUser tMock;
    @Mock
    private Pageable pageable;
    @Mock
    private List<ValidateUser> validateUsers;
    @Mock
    private List<ValidatePeople> validatePeople;
    @Mock
    private ValidatePeople validatePeople1;
    @Mock
    private ValidatePeople validatePeople2;
    @Mock
    private ValidateUser validateUsers1;
    @Mock
    private ValidateUser validateUsers2;
    SimpleUser simpleUser = randomT();
    Address address;
    User user;
    People people;

    @BeforeEach
    void setup(){

        address = new Address(
                null,
                "12345-678",
                "StateName",
                "CityName",
                "Neighborhood",
                "PropertyLocation"
        );

        user = new User(
                null,
                "loginName",
                "password",
                Role.USER
        );


        people = new People(
                1L,
                "teste",
                LocalDate.of(2024, 6, 14),  // birthDay
                "123.456.789-13",
                "1291234-4321",
                address,
                user
        );

        MockitoAnnotations.openMocks(this);
        simpleUserService = new SimpleUserService(
                Arrays.asList(validatePeople1, validatePeople2),
                Arrays.asList(validateUsers1, validateUsers2)
        );
    }

    @DisplayName("Testando a seleção de todos os usuarios")
    @Test
    void test01(){
        Page<SimpleUser> page = new PageImpl<>(List.of(simpleUser));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(page ,service.findAll(pageable));
        then(repository).should().findAll(any(Pageable.class));
    }
    @DisplayName("Testando a seleção de um usuario")
    @Test
    void test02(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(simpleUser));
        assertEquals(simpleUser ,service.findById(simpleUser.getId()));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando erro ao não achar um usuario")
    @Test
    void test03(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> service.findById(1L));
        then(repository).should().findById(anyLong());
    }
    @DisplayName("Testando o salvamento da entidade usuario")
    @Test
    void test04(){
        SimpleUser simpleUser = randomT();
        when(repository.save(any())).thenReturn(tMock);
        assertEquals(tMock, service.save( new PeopleDto(simpleUser)));
        then(repository).should().save(any());
        then(tMock).should().passwordEncoder();
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());
    }

    @DisplayName("Testando a atualização da entidade usuario")
    @Test
    void test05(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(tMock));
        when(tMock.merge(any())).thenReturn(simpleUser);
        assertEquals(simpleUser, service.update(simpleUser.getId(),  new PeopleUpdateDto(simpleUser)));
        then(tMock).should().merge(any());
        then(validateUsers).should().forEach(any());
        then(validatePeople).should().forEach(any());
    }

    @DisplayName("Testando a atualização da entidade usuario - Método Validate | People")
    @Test
    void test06(){

        simpleUserService2.validate(people);

        assertEquals("teste", people.getName());
        assertEquals(LocalDate.of(2024, 6, 14), people.getBirthDay());
        assertEquals("123.456.789-13", people.getCpf());
        assertEquals("1291234-4321", people.getPhone());
        assertEquals(address, people.getAddress());
        assertEquals(user, people.getUser());

        verify(simpleUserService2, atLeastOnce()).validate(any(People.class));
    }

    @DisplayName("Testando a atualização da entidade usuario - Método Validate | ForEach")
    @Test
    void test07() {

        people = mock(People.class);
        user = mock(User.class);
        when(people.getUser()).thenReturn(user);

        simpleUserService.validate(people);

        verify(validatePeople1, times(1)).validate(people);
        verify(validatePeople2, times(1)).validate(people);
        verify(validateUsers1, times(1)).validate(user);
        verify(validateUsers2, times(1)).validate(user);
    }

    @DisplayName("Testando a exclusão da entidade usuario")
    @Test
    void test08(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(tMock));
        service.delete(anyLong());
        then(repository).should().delete(any());
    }

    public SimpleUser randomT() {
        return new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73 988888888", randomAddress(), randomUser());
    }

    public Address randomAddress(){
        return new Address(1L, "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }

    public User randomUser(){
        return new User(1L, "artur@gmail.com", "123", Role.USER);
    }
}