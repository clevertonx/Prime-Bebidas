package br.com.prime.prime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prime.prime.Services.UsuarioService;
import br.com.prime.prime.dto.LoginDTO;
import br.com.prime.prime.dto.LoginRequestDTO;
import br.com.prime.prime.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping(path = "/login")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Login")
    @ApiResponse(responseCode = "200")
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<LoginDTO> login(
            @RequestBody @Valid LoginRequestDTO loginRequestDTO) throws Exception {
        LoginDTO loginDTO = usuarioService.login(loginRequestDTO);
        HttpStatus status = HttpStatus.OK;
        if (loginDTO.getId() == -1) {
            status = HttpStatus.FORBIDDEN;
        }
        return ResponseEntity.status(status).body(loginDTO);
    }
}
