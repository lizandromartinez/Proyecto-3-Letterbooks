package mx.unam.ciencias.myp.letterbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * <p>
 * Esta clase sirve como punto de entrada del sistema y se encarga
 * de inicializar Spring y arrancar la aplicación.
 * </p>
 */
@SpringBootApplication
public class Aplicacion {

    /**
     * Método principal que arranca la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos (en caso de que existan)
     */
    public static void main(String[] args) {
	SpringApplication.run(Aplicacion.class, args);
    }
}
