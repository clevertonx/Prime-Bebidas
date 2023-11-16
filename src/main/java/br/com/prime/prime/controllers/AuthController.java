package br.com.prime.prime.controllers;

import br.com.prime.prime.Services.UsuarioService;
import br.com.prime.prime.dto.LoginTokenJWTDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.event.listener.RegistrationCompleteEventListener;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenservice;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegistrationCompleteEventListener eventListener;


    @Operation(summary = "Logar usuario")
    @ApiResponse(responseCode = "201")
    @PostMapping("/usuario/login")
    public ResponseEntity<LoginTokenJWTDTO> login(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(usuarioRequestDTO.getEmail(), usuarioRequestDTO.getSenha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenservice.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginTokenJWTDTO(tokenJWT));
    }

}