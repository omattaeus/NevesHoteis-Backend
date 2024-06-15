package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements PeopleService<Admin> {
    @Autowired
    private AdminRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private List<ValidateUser> validateUsers;
    @Autowired
    private List<ValidatePeople> validatePeople;
    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Admin save(PeopleDto peopleDto) {
        Admin adminDto = new Admin(peopleDto);
        validate(adminDto);
        Admin admin= repository.save(adminDto);
        admin.passwordEncoder();
        return admin;
    }

    @Override
    public Admin update(long id, PeopleUpdateDto dto) {
        Admin adminDto = new Admin(dto);
        validate(adminDto);
        Admin admin = findById(id);
        return (Admin) admin.merge(adminDto);
    }

    @Override
    public Admin findById(Long id) {
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity not found"));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }
    public void validate(People people){
        validatePeople.forEach(b ->b.validate(people));
        validateUsers.forEach(b-> b.validate(people.getUser()));
    }
}
