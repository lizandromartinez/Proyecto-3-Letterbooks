package mx.unam.ciencias.myp.letterbooks.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de la aplicación.
 * <p>
 * Define los beans relacionados con Spring Security, incluyendo el
 * codificador de contraseñas y la configuración básica del filtro de seguridad HTTP.
 * </p>
 */
@Configuration
public class SeguridadConfig {

    /**
     * Bean encargado de encriptar contraseñas utilizando BCrypt.
     * BCrypt es un algoritmo de hash resistente a ataques de fuerza bruta.
     *
     * @return un codificador de contraseñas BCrypt
     */
    @Bean
    public PasswordEncoder codificadorContrasenas() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define la cadena de filtros de seguridad de Spring Security.
     * <p>
     * En esta configuración:
     * <ul>
     *   <li>Se desactiva CSRF</li>
     *   <li>Se permite el acceso a todas las rutas sin autenticación</li>
     * </ul>
     * </p>
     *
     * @param http objeto HttpSecurity usado para configurar la seguridad web
     * @return SecurityFilterChain configurado
     * @throws Exception si ocurre un error al construir la cadena de filtros
     */
    @Bean
    public SecurityFilterChain cadenaDeFiltros(HttpSecurity http) throws Exception {

	http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

	return http.build();
    }
    
}

