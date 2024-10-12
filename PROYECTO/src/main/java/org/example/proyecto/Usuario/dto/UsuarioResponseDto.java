package org.example.proyecto.Usuario.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioResponseDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
}