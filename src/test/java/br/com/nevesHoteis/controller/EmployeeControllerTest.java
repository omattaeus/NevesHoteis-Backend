package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.domain.Dto.PeopleCompleteDto;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends PeopleControllerTest<Employee, EmployeeService>{
    @Test
    @DisplayName("testando o selecionar")
    void test01() throws Exception {
        Employee employee= randomT();
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(service.findAll(any())).thenReturn(page);
        mockMvc.perform(get("/employee"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageCompleteDtoJacksonTester.write(page.map(PeopleCompleteDto::new)).getJson()));
    }


    @WithMockUser
    @Test
    @DisplayName("testando o selecionar")
    void test02() throws Exception {
        Employee employee = randomT();
        when(service.save(any())).thenReturn(employee);
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write( new PeopleDto(employee)).getJson()))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write( new PeopleCompleteDto(employee)).getJson()));
    }
    @Test
    @DisplayName("")
    void test03() throws Exception{
        Employee T = randomT();
        when(service.update(anyLong(), any())).thenReturn(T);
        mockMvc.perform(put("/employee/"+T.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(new PeopleDto(T)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(updateDtoJacksonTester.write( new PeopleUpdateDto(T)).getJson()));
    }

    @Test
    @DisplayName("")
    void test04() throws Exception{
        Employee employee =randomT();
        when(service.findById(anyLong())).thenReturn(employee);
        mockMvc.perform(
                        get("/employee/"+employee.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write(new PeopleCompleteDto(employee)).getJson()));
    }
    @Test
    @DisplayName("")
    void test05() throws Exception{
        mockMvc.perform(
                        delete("/employee/"+1))
                .andExpectAll(status().isNoContent());
        then(service).should().delete(anyLong());
    }

    @Test
    @DisplayName("Retornando erro ao testar uma entidade que não existe ")
    void test06() throws Exception {
        mockMvc.perform(get("/hotel/"))
                .andExpectAll(status().isNotFound());
    }

    @Override
    public Employee randomT() {
        return new Employee(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", randomAddress(), randomUser());
    }

    @Override
    public Address randomAddress() {
        return new Address(1L, "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }

    @Override
    public User randomUser() {
        return new User(1L, "artur@gmail.com", "Ar606060", Role.EMPLOYEE);
    }
}
