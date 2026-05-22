package mx.unam.ciencias.myp.letterbooks.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro encargado de interceptar cada petición HTTP
 * para validar el token JWT enviado por el cliente.
 */
@Component
public class FiltroAcceso extends OncePerRequestFilter {

    /**
     * Servicio encargado de la generación y validación de tokens JWT.
     * Se utiliza para extraer información del usuario y verificar su autenticidad
     * en cada petición HTTP.
     */
    @Autowired
    private TokenJWT tokenJWT;

    /**
     * Método principal del filtro.
     * Intercepta cada petición HTTP para extraer y validar el JWT.
     * Como estamos sobreescribiendo un método de Spring no se puede escribir en español
     *
     * @param solicitud petición HTTP entrante
     * @param respuesta respuesta HTTP saliente
     * @param cadenaFiltros cadena de filtros de Spring Security
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest solicitud,
        HttpServletResponse respuesta,
        FilterChain cadenaFiltros
    ) throws ServletException, IOException {

        System.out.println("FiltroAcceso: Request URI: " + solicitud.getRequestURI());

        // Obtener encabezado Authorization
        final String encabezadoAutorizacion =
            solicitud.getHeader("Authorization");

        System.out.println("FiltroAcceso: Authorization Header: " + encabezadoAutorizacion);

        String token = null;
        String nombreUsuario = null;

        // Verificar que el encabezado exista y empiece con Bearer
        if (encabezadoAutorizacion != null &&
            encabezadoAutorizacion.startsWith("Bearer ")) {

            // Eliminar Bearer
            token = encabezadoAutorizacion.substring(7);

            try {
                // Obtener el nombre de usuario desde el token
                nombreUsuario = tokenJWT.obtenerNombreUsuario(token);

            } catch (Exception excepcion) {

                System.out.println("Token inválido o expirado");

            }
        }

        /*
         * Si se obtuvo el usuario y todavía no existe
         * autenticación en el contexto de seguridad
         */
        if (nombreUsuario != null &&
            SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UsernamePasswordAuthenticationToken tokenAutenticacion =
                new UsernamePasswordAuthenticationToken(
                    nombreUsuario,
                    null,
                    Collections.emptyList()
                );

            tokenAutenticacion.setDetails(
                new WebAuthenticationDetailsSource()
                    .buildDetails(solicitud)
            );

            System.out.println("FiltroAcceso: Autenticando usuario: " + nombreUsuario);
            // Registrar autenticación
            SecurityContextHolder
                .getContext()
                .setAuthentication(tokenAutenticacion);
        }

        cadenaFiltros.doFilter(solicitud, respuesta);
    }
}
