package br.com.prime.prime.controllers;

import br.com.prime.prime.Services.UsuarioService;
import br.com.prime.prime.dto.LoginTokenJWTDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenservice;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Logar usuario")
    @ApiResponse(responseCode = "201")
    @PostMapping("/usuario/login")
    public ResponseEntity<LoginTokenJWTDTO> login(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(usuarioRequestDTO.getEmail(), usuarioRequestDTO.getSenha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenservice.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginTokenJWTDTO(tokenJWT));
    }

    @Operation(summary = "Cadastrar um novo usuario")
    @ApiResponse(responseCode = "201")
    @PostMapping(path = "/usuario/cadastro", consumes = {"application/json"})
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO novoUsuario) throws Exception {
        UsuarioResponseDTO usuarioResponse = usuarioService.criar(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }
}