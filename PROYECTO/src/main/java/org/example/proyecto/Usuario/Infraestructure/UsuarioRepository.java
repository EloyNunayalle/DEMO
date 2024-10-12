package org.example.proyecto.Usuario.Infraestructure;

import org.example.proyecto.Usuario.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
