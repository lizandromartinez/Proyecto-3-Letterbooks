package mx.unam.ciencias.myp.letterbooks.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuración encargada de exponer archivos estáticos
 * almacenados en el servidor para que puedan ser accedidos
 * desde el navegador mediante una URL pública.
 * Esta clase permite servir imágenes de usuarios, como
 * avatares y banners, desde el directorio definido en la
 * propiedad {@code app.upload.dir}.
 */
@Configuration
public class Archivos implements WebMvcConfigurer {

    /**
     * Directorio físico donde se almacenan las imágenes
     * subidas por los usuarios.
     */
    @Value("${app.upload.dir:almacenamiento/usuarios}")
    private String directorioImagenes;

    /**
     * Registra un manejador de recursos estáticos para permitir
     * el acceso a los archivos almacenados en el servidor.
     *
     * Las solicitudes realizadas a:
     * {@code /almacenamiento/usuarios/**}
     * serán redirigidas al directorio físico configurado
     * en {@code directorioImagenes}.
     *
     * No se puede traducir al español porque estamos sobreescribiendo un método de Spring
     * @param registro registro de manejadores de recursos
     * utilizado por Spring MVC.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registro) {

        Path directorio = Paths.get(directorioImagenes).toAbsolutePath().normalize();
        String ubicacion = directorio.toUri().toString();
        if (!ubicacion.endsWith("/")) {
            ubicacion += "/";
        }

        registro.addResourceHandler("/almacenamiento/usuarios/**")
            .addResourceLocations(ubicacion);
    }
}
