package org.example.proyecto.Usuario.Domain;


import org.example.proyecto.Usuario.Infraestructure.UsuarioRepository;
import org.example.proyecto.Usuario.dto.UsuarioRequestDto;
import org.example.proyecto.Usuario.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioResponseDto registrarUsuario(UsuarioRequestDto requestDTO) {
        Usuario usuario = modelMapper.map(requestDTO, Usuario.class);
        usuario.setCreatedAt(LocalDateTime.now());

        usuario = usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public UsuarioResponseDto buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(value -> modelMapper.map(value, UsuarioResponseDto.class)).orElse(null);
    }

    public List<UsuarioResponseDto> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDto actualizarUsuario(Long id, UsuarioRequestDto requestDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            modelMapper.map(requestDTO, usuario);
            usuario = usuarioRepository.save(usuario);

            return modelMapper.map(usuario, UsuarioResponseDto.class);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}