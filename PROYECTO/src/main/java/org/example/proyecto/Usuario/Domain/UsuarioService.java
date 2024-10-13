package org.example.proyecto.Usuario.Domain;


import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.Usuario.dto.UsuarioRequestDto;
import org.example.proyecto.Usuario.dto.UsuarioResponseDto;
import org.example.proyecto.event.usuario.UsuarioCreadoEvent;
import org.example.proyecto.exception.InvalidUserFieldException;
import org.example.proyecto.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public UsuarioResponseDto registrarUsuario(UsuarioRequestDto requestDTO) {
        // Validar nombre
        if (requestDTO.getFirstname() == null || requestDTO.getFirstname().isBlank()) {
            throw new InvalidUserFieldException("El nombre no puede estar vacío");
        }
        if (requestDTO.getFirstname().length() > 50) {
            throw new InvalidUserFieldException("El nombre no puede tener más de 50 caracteres");
        }

        // Validar apellido
        if (requestDTO.getLastname() == null || requestDTO.getLastname().isBlank()) {
            throw new InvalidUserFieldException("El apellido no puede estar vacío");
        }
        if (requestDTO.getLastname().length() > 50) {
            throw new InvalidUserFieldException("El apellido no puede tener más de 50 caracteres");
        }

        // Validar correo
        if (requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()) {
            throw new InvalidUserFieldException("El correo no puede estar vacío");
        }
        if (!requestDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidUserFieldException("El correo no es válido");
        }

        // Validar teléfono
        if (requestDTO.getPhone() == null || requestDTO.getPhone().length() < 7 || requestDTO.getPhone().length() > 15) {
            throw new InvalidUserFieldException("El teléfono debe tener entre 7 y 15 dígitos");
        }

        // Validar contraseña
        if (requestDTO.getPassword() == null || requestDTO.getPassword().isBlank()) {
            throw new InvalidUserFieldException("La contraseña no puede estar vacía");
        }
        if (requestDTO.getPassword().length() < 8) {
            throw new InvalidUserFieldException("La contraseña debe tener al menos 8 caracteres");
        }

        // Validar dirección
        if (requestDTO.getAddress() == null || requestDTO.getAddress().isBlank()) {
            throw new InvalidUserFieldException("La dirección no puede estar vacía");
        }
        if (requestDTO.getAddress().length() > 100) {
            throw new InvalidUserFieldException("La dirección no puede tener más de 100 caracteres");
        }

        // Crear el usuario
        Usuario usuario = modelMapper.map(requestDTO, Usuario.class);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario = usuarioRepository.save(usuario);

        eventPublisher.publishEvent(new UsuarioCreadoEvent(this, usuario));

        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public UsuarioResponseDto buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public List<UsuarioResponseDto> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDto actualizarUsuario(Long id, UsuarioRequestDto requestDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));

        modelMapper.map(requestDTO, usuarioExistente);
        usuarioRepository.save(usuarioExistente);

        return modelMapper.map(usuarioExistente, UsuarioResponseDto.class);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));
        usuarioRepository.delete(usuario);
    }
}

