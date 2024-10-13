package org.example.proyecto.auth.application;

import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.Domain.UsuarioService;
import org.example.proyecto.auth.domain.AuthenticationService;
import org.example.proyecto.auth.dto.JwtAuthResponse;
import org.example.proyecto.auth.dto.LoginRequest;
import org.example.proyecto.auth.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterRequest request) {


        JwtAuthResponse response = authenticationService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request) {
            JwtAuthResponse response = authenticationService.signin(request);
            return ResponseEntity.ok(response);
    }
}
