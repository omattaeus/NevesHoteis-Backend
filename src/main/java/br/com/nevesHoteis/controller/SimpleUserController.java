package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.service.SimpleUserService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/simple-user")
@RestController
public class SimpleUserController extends PeopleController<SimpleUser, SimpleUserService> {
}
