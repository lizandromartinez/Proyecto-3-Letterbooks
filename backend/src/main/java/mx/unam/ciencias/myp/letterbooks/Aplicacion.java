package mx.unam.ciencias.myp.letterbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal.
 * Configura el contexto de Spring Boot, escaneo de componentes y persistencia.
 */
@SpringBootApplication
public class Aplicacion {

    /**
     * Método principal que arranca la ejecución del framework.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(Aplicacion.class, args);
    }
}
