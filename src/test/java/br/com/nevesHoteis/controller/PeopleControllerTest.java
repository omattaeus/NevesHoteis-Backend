package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.domain.Dto.PeopleCompleteDto;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;

import br.com.nevesHoteis.service.PeopleService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public abstract class PeopleControllerTest<T extends People, S extends PeopleService<T>> {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected S service;
    @Autowired
    protected JacksonTester<PeopleCompleteDto> completeDtoJacksonTester;
    @Autowired
    protected JacksonTester<PeopleUpdateDto> updateDtoJacksonTester;
    @Autowired
    protected JacksonTester<PeopleDto> dtoJacksonTester;
    @Autowired
    protected JacksonTester<Page<PeopleCompleteDto>> pageCompleteDtoJacksonTester;


    public abstract T randomT();
    public abstract Address randomAddress();
    public abstract User randomUser();

}
