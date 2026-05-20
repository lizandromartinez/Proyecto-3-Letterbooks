package mx.unam.ciencias.myp.letterbooks.configuracion;

import mx.unam.ciencias.myp.letterbooks.seguridad.FiltroAcceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Arrays;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
/**
 * Clase de configuración para la seguridad web del sistema.
 * Define las reglas de acceso a las rutas y el encriptador de contraseñas.
 */
@Configuration
@EnableWebSecurity
public class Seguridad {

    /**
     * Filtro encargado de validar los tokens JWT
     * enviados en las peticiones HTTP antes de que lleguen a los
     * controladores protegidos.
     */
    @Autowired
    private FiltroAcceso filtroAcceso;
    
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

	http.addFilterBefore(filtroAcceso, UsernamePasswordAuthenticationFilter.class);

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

    /**
     * Define la configuración de CORS permitiendo peticiones desde el frontend.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permitir solicitudes desde localhost en el puerto 80 (y 3000 si usas npm run dev)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost", "http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
