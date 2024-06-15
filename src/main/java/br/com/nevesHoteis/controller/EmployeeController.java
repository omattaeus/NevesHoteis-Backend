package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Employee;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.service.EmployeeService;
import br.com.nevesHoteis.service.SimpleUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employee")
@RestController
public class EmployeeController extends PeopleController<Employee, EmployeeService> {
}
