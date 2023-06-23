package br.com.prime.prime.controllers;

import br.com.prime.prime.Services.TokenService;
import br.com.prime.prime.dto.LoginDTO;
import br.com.prime.prime.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenservice;
    @PostMapping("/usuario/login")
    public String login(@RequestBody LoginDTO login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(login.email(), login.senha());

        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var usuario = (Usuario) authenticate.getPrincipal();

        return tokenservice.gerarToken(usuario);

    }
}
