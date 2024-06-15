package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.SimpleUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleUserService implements PeopleService<SimpleUser> {
    @Autowired
    private SimpleUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private List<ValidateUser> validateUsers;
    @Autowired
    private List<ValidatePeople> validatePeople;

    public SimpleUserService(List<ValidatePeople> validatePeople, List<ValidateUser> validateUsers) {
        this.validatePeople = validatePeople;
        this.validateUsers = validateUsers;
    }


    public Page<SimpleUser> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
    @Transactional
    public SimpleUser save(PeopleDto peopleDto) {
        SimpleUser simpleUser = new SimpleUser(peopleDto);
        validate(simpleUser);
        SimpleUser userEntity = repository.save(simpleUser);
        userEntity.passwordEncoder();
        return userEntity;
    }
    @Transactional
    public SimpleUser update(long id, PeopleUpdateDto peopleUpdateDto) {
        SimpleUser simpleUserDto = new SimpleUser(peopleUpdateDto);
        validate(simpleUserDto);
        SimpleUser simpleUser = findById(id);
        return (SimpleUser) simpleUser.merge(simpleUserDto);
    }
    public SimpleUser findById(Long id){
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity not found"));
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public void validate(People people){
        validatePeople.forEach(b ->b.validate(people));
        validateUsers.forEach(b-> b.validate(people.getUser()));
    }

}
