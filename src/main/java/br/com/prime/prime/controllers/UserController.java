package br.com.prime.prime.controllers;

import br.com.prime.prime.Services.UserService;
import br.com.prime.prime.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<Usuario> getUsers(){
        return userService.getUsers();
    }
}
