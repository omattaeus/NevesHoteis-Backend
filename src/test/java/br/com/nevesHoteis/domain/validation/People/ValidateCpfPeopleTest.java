package br.com.nevesHoteis.domain.validation.People;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ValidateCpfPeopleTest {
    @InjectMocks
    private ValidateCpfPeople validate;
    @Test
    @DisplayName("Testando copfs válidos")
    void test01(){
        People people = randomPeople();
        people.setCpf("063.726.075-90");
        validate.validate(people);
        assertDoesNotThrow( ()->validate.validate(people));
        people.setCpf("472.221.360-70");
        assertDoesNotThrow( ()->validate.validate(people));
        people.setCpf("833.624.870-68");
        assertDoesNotThrow( ()->validate.validate(people));
        people.setCpf("567.947.890-00");
        assertDoesNotThrow( ()->validate.validate(people));
        people.setCpf("036.797.520-32");
        assertDoesNotThrow( ()->validate.validate(people));
    }
    @Test
    @DisplayName("Testando cpfs inválidos")
    void test02(){
        People people = randomPeople();
        people.setCpf("123.456.780-90");
        assertThrows(ValidateUserException.class, ()->validate.validate(people));
        people.setCpf("472.220.460-70");
        assertThrows(ValidateUserException.class, ()->validate.validate(people));
        people.setCpf("989.624.870-68");
        assertThrows(ValidateUserException.class, ()->validate.validate(people));
        people.setCpf("56.794.7890-01");
        assertThrows(ValidateUserException.class, ()->validate.validate(people));
        people.setCpf("036.797.520-30");
        assertThrows(ValidateUserException.class, ()->validate.validate(people));
    }

    public People randomPeople() {
        return new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "02627433024", "73 988888888", new Address(), new User());
    }
}