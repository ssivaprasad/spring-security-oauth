package com.ssp.apps.oauth.resource.server.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<String> getAllUsers() {
        return Arrays.asList("Siva", "Prasad", "Somarouthu");
    }

}
