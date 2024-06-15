package br.com.nevesHoteis.domain.validation.User;

import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidatePasswordUserTest {
    @InjectMocks
    private ValidatePasswordUser validate;

    @Test
    @DisplayName("Testando senhas válidas")
    void test01() {
        User user = randomUser();
        assertDoesNotThrow(()-> validate.validate(user));
        user.setPassword("Po890780");
        assertDoesNotThrow(()->validate.validate(user));
        user.setPassword("Ar890423");
        assertDoesNotThrow(()->validate.validate(user));
        user.setPassword("Xfidjsf2");
        assertDoesNotThrow(()->validate.validate(user));
        user.setPassword("Blabla12");
        assertDoesNotThrow(()->validate.validate(user));
    }
    @Test
    @DisplayName("Testando senhas inváldas")
    void test02() {
        User user = randomUser();
        user.setPassword("Artur-f");
        assertThrows(ValidateUserException.class ,()-> validate.validate(user));
        user.setPassword("12345678");
        assertThrows(ValidateUserException.class ,()->validate.validate(user));
        user.setPassword("ArturDes");
        assertThrows(ValidateUserException.class ,()->validate.validate(user));
        user.setPassword("c1234566");
        assertThrows(ValidateUserException.class ,()->validate.validate(user));
        user.setPassword("A90B56C0");
        assertThrows(ValidateUserException.class ,()->validate.validate(user));
    }

    public User randomUser() {
        return new User(1L, "artur@gmail.com", "Po890780", Role.USER);
    }
}