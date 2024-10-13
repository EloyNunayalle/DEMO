package org.example.proyecto.auth.utils;


import org.example.proyecto.Usuario.Domain.Role;
import org.example.proyecto.Usuario.Domain.UserDetailsServiceImpl;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.Domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public boolean isAdminOrResourceOwner(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica si hay un usuario autenticado.
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // En este caso, el correo es el nombre de usuario.

        // Busca al usuario por su email y rol.
        Usuario usuario = userDetailsService.loadUserByUsername(email);

        // Verifica si el usuario es el propietario del recurso o es administrador.
        return usuario.getId().equals(id) || usuario.getRole().equals(Role.ADMIN);
    }

    public boolean getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return true;
    }
}
