package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.domain.Dto.PeopleCompleteDto;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.service.AdminService;
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

public class AdminControllerTest extends PeopleControllerTest<Admin, AdminService>{
    @Test
    @DisplayName("testando o selecionar")
    void test01() throws Exception {
        Admin admin= randomT();
        Page<Admin> page = new PageImpl<>(List.of(admin));
        when(service.findAll(any())).thenReturn(page);
        mockMvc.perform(get("/admin"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageCompleteDtoJacksonTester.write(page.map(PeopleCompleteDto::new)).getJson()));
    }


    @WithMockUser
    @Test
    @DisplayName("testando o selecionar")
    void test02() throws Exception {
        Admin admin = randomT();
        when(service.save(any())).thenReturn(admin);
        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write( new PeopleDto(admin)).getJson()))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write( new PeopleCompleteDto(admin)).getJson()));
    }
    @Test
    @DisplayName("")
    void test03() throws Exception{
        Admin T = randomT();
        when(service.update(anyLong(), any())).thenReturn(T);
        mockMvc.perform(put("/admin/"+T.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(new PeopleDto(T)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(updateDtoJacksonTester.write( new PeopleUpdateDto(T)).getJson()));
    }

    @Test
    @DisplayName("")
    void test04() throws Exception{
        Admin admin =randomT();
        when(service.findById(anyLong())).thenReturn(admin);
        mockMvc.perform(
                        get("/admin/"+admin.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write(new PeopleCompleteDto(admin)).getJson()));
    }
    @Test
    @DisplayName("")
    void test05() throws Exception{
        mockMvc.perform(
                        delete("/admin/"+1))
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
    public Admin randomT() {
        return new Admin(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", randomAddress(), randomUser());
    }

    @Override
    public Address randomAddress() {
        return new Address(1L, "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
    }

    @Override
    public User randomUser() {
        return new User(1L, "artur@gmail.com", "Ar606060", Role.ADMIN);
    }
}
