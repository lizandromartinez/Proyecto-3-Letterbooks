package mx.unam.ciencias.myp.letterbooks.seguridad;

import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase encargada de la generación, lectura y validación de tokens JWT.
 * Utiliza una clave secreta para firmar los tokens de sesión.
 */
@Component
public class TokenJWT {

    /* Clave secreta. */
    private final String fraseSecreta = "LetterBooksSecretKeyParaFirmaDeTokens12345KNK_2026";
    private final SecretKey clave = Keys.hmacShaKeyFor(fraseSecreta.getBytes(StandardCharsets.UTF_8));
    
    /* Tiempo de validez del token (24 horas). */
    private final long tiempoExpiracion = 86400000;

    /**
     * Genera un token JWT para un usuario autenticado.
     * @param usuario El objeto usuario que ha iniciado sesión exitosamente.
     * @return Una cadena que representa el token JWT.
     */
    public String generarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getRol().name());
        claims.put("nombre_usuario", usuario.getNombreUsuario());

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(usuario.getNombreUsuario()) 
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
            .signWith(clave, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Extrae el nombre de usuario contenido en el token.
     * @param token El token JWT a procesar.
     * @return El nombre de usuario recuperado.
     */
    public String obtenerNombreUsuario(String token) {
        return obtenerReclamacion(token, Claims::getSubject); 
    }

    /**
     * Valida si un token pertenece al usuario y si no ha expirado.
     * @param token El token a validar.
     * @param usuario El usuario contra el cual validar.
     * @return true si es válido, false en caso contrario.
     */
    public boolean esTokenValido(String token, Usuario usuario) {
        final String nombreUsuario = obtenerNombreUsuario(token); 
        return (nombreUsuario.equals(usuario.getNombreUsuario()) && !estaExpirado(token)); 
    }

    /* Determina si el token ya superó su fecha de expiración. */
    private boolean estaExpirado(String token) {
        return obtenerReclamacion(token, Claims::getExpiration).before(new Date());
    }

    /* Extraer información (claims) del token. */
    private <T> T obtenerReclamacion(String token, Function<Claims, T> reclamacionesResolver) {
        final Claims claims = Jwts.parserBuilder()
            .setSigningKey(clave)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return reclamacionesResolver.apply(claims);
    }
}
