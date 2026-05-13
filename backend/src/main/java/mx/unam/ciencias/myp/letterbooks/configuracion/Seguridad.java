package mx.unam.ciencias.myp.letterbooks.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración para la seguridad web del sistema.
 * Define las reglas de acceso a las rutas y el encriptador de contraseñas.
 */
@Configuration
@EnableWebSecurity
public class Seguridad {

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * @param http El objeto HttpSecurity a configurar.
     * @return La cadena de filtros de seguridad construida.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain cadenaFiltrosSeguridad(HttpSecurity http) throws Exception {
        http
            .cors(org.springframework.security.config.Customizer.withDefaults())
            .csrf(csrf -> csrf.disable()) // Desactivamos CSRF por ser una API REST sin estado
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // Rutas de login/registro públicas
                .anyRequest().authenticated() // Todo lo demás requiere autenticación
            );
        return http.build();
    }

    /**
     * Define el bean para el encriptador de contraseñas usando BCrypt.
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }
}
