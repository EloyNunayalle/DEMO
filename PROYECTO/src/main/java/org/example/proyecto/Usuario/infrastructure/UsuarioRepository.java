package org.example.proyecto.Usuario.infrastructure;

import org.example.proyecto.Usuario.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
