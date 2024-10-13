package org.example.proyecto.auth.domain;


import org.example.proyecto.Usuario.Domain.Role;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.Domain.UsuarioService;
import org.example.proyecto.Usuario.dto.UsuarioRequestDto;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.auth.dto.JwtAuthResponse;
import org.example.proyecto.auth.dto.LoginRequest;
import org.example.proyecto.auth.dto.RegisterRequest;
import org.example.proyecto.auth.exception.UserAlreadyExistException;
import org.example.proyecto.config.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthenticationService(UsuarioService usuarioService, UsuarioRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    public JwtAuthResponse signin(LoginRequest req) {
        Optional<Usuario> user;
        user = userRepository.findByEmail(req.getUsername());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public JwtAuthResponse signup(RegisterRequest req) {
        Optional<Usuario> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistException("El correo electrónico ya está registrado");
        }

        // Crear un nuevo usuario con rol USER o ADMIN dependiendo del registro
        Usuario newUser = modelMapper.map(req, Usuario.class);

        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        // Asignar el rol de acuerdo al campo `isAdmin`
        if (req.getIsAdmin()) {
            newUser.setRole(Role.ADMIN);
        } else {
            newUser.setRole(Role.USER);
        }

        // Guardar el nuevo usuario
        usuarioRepository.save(newUser);

        // Generar la respuesta con el token JWT
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(newUser));
        return response;

        //a
    }
}


