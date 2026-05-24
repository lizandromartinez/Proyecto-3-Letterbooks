package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.Perfil;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas del repositorio de perfiles.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = "mx.unam.ciencias.myp.letterbooks.modelo")
@EnableJpaRepositories(basePackages = "mx.unam.ciencias.myp.letterbooks.repositorio")
public class TestPerfilRepositorio {

    /** Repositorio de perfiles. */
    @Autowired
    private PerfilRepositorio perfilRepositorio;

    /** Componente de JPA que se encarga de manejar entidades y operaciones contra la base de datos */
    @Autowired
    private TestEntityManager entityManager;
    
    /**
     * Verifica que se encuentre un perfil por usuario.
     */
    @Test
   public void encontrarPorUsuarioDeberiaRegresarPerfil() {

	Usuario usuario = new Usuario();
	usuario.setNombreUsuario("usuarioTest1");
	usuario.setCorreo("test1@correo.com");
	usuario.setContrasena("1234");

	usuario = entityManager.persistAndFlush(usuario);

	Perfil perfil = new Perfil();
	perfil.setUsuario(usuario);

	entityManager.persistAndFlush(perfil);

	Optional<Perfil> resultado =
            perfilRepositorio.encontrarPorUsuario(
		usuario.getIdUsuario()
            );

	assertTrue(resultado.isPresent());
    }
    /**
     * Verifica que se regrese vacío si no existe perfil.
     */
    @Test
    public void encontrarPorUsuarioDeberiaRegresarEmpty() {

        Optional<Perfil> resultado =
            perfilRepositorio.encontrarPorUsuario(999);

        assertTrue(resultado.isEmpty());
    }

    /**
     * Verifica que exista un perfil asociado al usuario.
     */
    @Test
    public void existePorUsuarioDeberiaRegresarTrue() {

	Usuario usuario = new Usuario();
	usuario.setNombreUsuario("usuarioTest2");
	usuario.setCorreo("test2@correo.com");
	usuario.setContrasena("1234");

	usuario = entityManager.persistAndFlush(usuario);

	Perfil perfil = new Perfil();
	perfil.setUsuario(usuario);

	entityManager.persistAndFlush(perfil);

	boolean existe =
            perfilRepositorio.existePorUsuario(
		usuario.getIdUsuario()
            );

	assertTrue(existe);
    }
    /**
     * Verifica que regrese false si el perfil no existe.
     */
    @Test
    public void existePorUsuarioDeberiaRegresarFalse() {
        boolean existe = perfilRepositorio.existePorUsuario(999);
        assertFalse(existe);
    }
}
