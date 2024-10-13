package org.example.proyecto.config;

import lombok.RequiredArgsConstructor;
import org.example.proyecto.Usuario.Domain.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UsuarioService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()

                        // Acceso de ADMIN
                        .requestMatchers("/item/**").hasAuthority("ADMIN")
                        .requestMatchers("/category/**").hasAuthority("ADMIN")
                        .requestMatchers("/usuarios/**").hasAuthority("ADMIN")
                        .requestMatchers("/shipments/**").hasAuthority("ADMIN")
                        .requestMatchers("/agreements/**").hasAuthority("ADMIN")
                        .requestMatchers("/ratings/**").hasAuthority("ADMIN")

                        // Permitir a los usuarios acceso a sus propios ítems
                        .requestMatchers(HttpMethod.POST, "/item").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT, "/item/**").hasAuthority("USER")
                        .requestMatchers(HttpMethod.DELETE, "/item/**").hasAuthority("USER")
                        .requestMatchers("/item/category/**").hasAuthority("USER")
                        .requestMatchers("/item/user/**").hasAuthority("USER")
                        .requestMatchers("/item/mine").hasAuthority("USER")


                        // Configuración de acceso a categorías
                        .requestMatchers(HttpMethod.GET, "/category").hasAnyAuthority("USER") // Ver todas las categorías
                        .requestMatchers(HttpMethod.GET, "/category/{id}").hasAnyAuthority("USER") // Ver una categoría por ID

                        // Configuración de acceso a raiting
                        .requestMatchers(HttpMethod.POST, "/ratings/crear").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET, "/ratings/usuario/{usuarioId}").hasAuthority("USER")
                        .requestMatchers(HttpMethod.DELETE, "/{id}").hasAuthority("USER")

                        // Configuración de acceso a agreements
                        .requestMatchers(HttpMethod.POST, "/agreements/crear").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT, "/agreements/{id}/accept").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT, "/agreements/{id}/reject").hasAuthority("USER")

                        .requestMatchers("/usuarios/me").hasRole("USER")
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userService.userDetailsService());
        return authProvider;
    }


    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ADMIN > DRIVER");
        hierarchy.setHierarchy("ADMIN > PASSENGER");

        return hierarchy;
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        expressionHandler.setDefaultRolePrefix("");

        return expressionHandler;
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
