package mx.unam.ciencias.myp.letterbooks.configuracion;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración de CORS para permitir
 * solicitudes desde el frontend.
 */
@Configuration
public class Cors {

    /**
     * Configura las reglas CORS del sistema.
     * Permite solicitudes desde el frontend
     * ejecutándose en localhost.
     *
     * @return configuración de CORS utilizada
     *         por Spring Security.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuracion = new CorsConfiguration();

	//Permitir solicitudes desde el frontend
        configuracion.setAllowedOrigins(Arrays.asList(
            "http://localhost",
            "http://localhost:3000",
            "http://127.0.0.1:3000"
        ));
        configuracion.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuracion.setAllowedHeaders(Arrays.asList("*"));
        configuracion.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**",configuracion);

        return fuente;
    }
}
