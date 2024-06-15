package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.domain.Dto.PeopleCompleteDto;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.service.PeopleService;
import br.com.nevesHoteis.service.SimpleUserService;
import br.com.nevesHoteis.service.UserService;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

class SimpleUserControllerTest extends PeopleControllerTest<SimpleUser, SimpleUserService> {


    @Test
    @DisplayName("testando o selecionar")
    void test01() throws Exception {
        SimpleUser simpleUser= randomT();
        Page<SimpleUser> page = new PageImpl<>(List.of(simpleUser));
        when(service.findAll(any())).thenReturn(page);
        mockMvc.perform(get("/simple-user"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageCompleteDtoJacksonTester.write(page.map(PeopleCompleteDto::new)).getJson()));
    }


    @WithMockUser
    @Test
    @DisplayName("testando o selecionar")
    void test02() throws Exception {
        SimpleUser simpleUser = randomT();
        when(service.save(any())).thenReturn(simpleUser);
        mockMvc.perform(post("/simple-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write( new PeopleDto(simpleUser)).getJson()))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write( new PeopleCompleteDto(simpleUser)).getJson()));
    }
    @Test
    @DisplayName("")
    void test03() throws Exception{
        SimpleUser T = randomT();
        when(service.update(anyLong(), any())).thenReturn(T);
        mockMvc.perform(put("/simple-user/"+T.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(new PeopleDto(T)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(updateDtoJacksonTester.write( new PeopleUpdateDto(T)).getJson()));
    }

    @Test
    @DisplayName("")
    void test04() throws Exception{
        SimpleUser simpleUser =randomT();
        when(service.findById(anyLong())).thenReturn(simpleUser);
        mockMvc.perform(
                        get("/simple-user/"+simpleUser.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write(new PeopleCompleteDto(simpleUser)).getJson()));
    }
    @Test
    @DisplayName("")
    void test05() throws Exception{
        mockMvc.perform(
                        delete("/simple-user/"+1))
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
    public SimpleUser randomT() {
        return new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", randomAddress(), randomUser());
    }
    @Override
    public Address randomAddress(){
        return new Address(1L, "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }
    @Override
    public User randomUser(){
        return new User(1L, "artur@gmail.com", "Ar606060", Role.USER);
    }
}