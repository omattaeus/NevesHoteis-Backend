package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.service.AdminService;
import br.com.nevesHoteis.service.SimpleUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminController extends PeopleController<Admin, AdminService> {
}
