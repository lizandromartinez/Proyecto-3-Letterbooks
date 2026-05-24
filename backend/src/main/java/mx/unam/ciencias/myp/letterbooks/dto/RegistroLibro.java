package mx.unam.ciencias.myp.letterbooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;


/**
 * Representa el formulario de entrada de datos para la vista de subir o registrar un libro.
 * Esta clase actúa como un molde intermedio (DTO) para capturar  el texto plano en formato
 * JSON enviado desde el Frontend. El framework Spring Boot, en conjunto con la biblioteca
 * Jackson, orquesta la traducción del JSON de la siguiente manera:
 *    - Instancia la clase utilizando el constructor vacío por defecto.
 *    - Utiliza los métodos setters de manera automatizada tras bambalinas para inyectar 
 *      cada valor del JSON en su atributo correspondiente.
 *
 * Una vez mapeado y validadas sus restricciones mediante Bean Validation, se entrega el objeto 
 * completamente estructurado a la capa de servicio para su posterior procesamiento.
 */
public class RegistroLibro {

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 255, message = "El título no puede superar los 255 caracteres")
    private String titulo;

    @Size(max = 10000, message = "La sinopsis supera el límite de caracteres permitido")
    private String sinopsis;

    @Size(max = 255, message = "La ruta de la imagen no puede superar los 255 caracteres")
    private String imagen;

    @Min(value = 1, message = "El libro debe tener al menos 1 página")
    private Integer paginas;

    @Max(value = 2026, message = "El año de publicación no puede ser en el futuro")
    private Integer ano;

    @Size(max = 20, message = "El ISBN no puede superar los 20 caracteres")
    private String isbn;

    @NotNull(message = "Debe seleccionar un género válido")
    private Integer idGenero;

    @NotNull(message = "Debe seleccionar un autor válido")
    private Integer idAutor;

    @NotNull(message = "Debe seleccionar una editorial válida")
    private Integer idEditorial;

    /**
     * Constructor vacío por defecto.
     * Requerido obligatoriamente por la biblioteca Jackson para poder iniciar 
     * el proceso de deserialización y construcción del objeto desde el JSON.
     */
    public RegistroLibro() {}

    
    /**
     * Obtiene el título del libro. 
     * Invocado por el validador de Spring para verificar la restricción {@code @NotBlank} 
     * y por la capa de Servicio para extraer el dato hacia la entidad de persistencia.
     * @return Cadena de texto con el título del libro.
     */
    public String getTitulo() { 
        return this.titulo; 
    }

    /**
     * Obtiene la sinopsis del libro.
     * Invocado por la capa de Servicio para leer el resumen capturado en el formulario JSON.
     * @return Cadena de texto con la sinopsis del libro.
     */
    public String getSinopsis() { 
        return this.sinopsis; 
    }

    /**
     * Obtiene la ruta o URL de la imagen de portada.
     * Invocado por la capa de Servicio para mapear el recurso multimedia al modelo final.
     * @return Cadena de texto con la ubicación o enlace de la imagen.
     */
    public String getImagen() { 
        return this.imagen; 
    }

    /**
     * Obtiene el número total de páginas del libro.
     * Invocado por Spring para validar la restricción {@code @Min} y por el Servicio para su persistencia.
     * @return Un entero con la cantidad de páginas del libro.
     */
    public Integer getPaginas() { 
        return this.paginas; 
    }

    /**
     * Obtiene el año de publicación del libro.
     * Invocado por Spring para validar la restricción {@code @Max} y por la capa de lógica de negocio.
     * @return Un entero que representa el año de publicación.
     */
    public Integer getAno() { 
        return this.ano; 
    }

    /**
     * Obtiene el código ISBN del libro.
     * Invocado por el Servicio para realizar validaciones de unicidad contra la base de datos.
     * @return Cadena de texto con el valor del ISBN.
     */
    public String getIsbn() { 
        return this.isbn; 
    }

    /**
     * Obtiene el identificador numérico del género seleccionado.
     * Utilizado por el Servicio para buscar la entidad {@code Genero} real mediante su repositorio.
     * @return Identificador único del género literario proveniente del JSON.
     */
    public Integer getIdGenero() { 
        return this.idGenero; 
    }

    /**
     * Obtiene el identificador numérico del autor seleccionado.
     * Utilizado por el Servicio para buscar la entidad {@code Autor} real mediante su repositorio.
     * @return Identificador único del autor proveniente del JSON.
     */
    public Integer getIdAutor() { 
        return this.idAutor; 
    }

    /**
     * Obtiene el identificador numérico de la editorial seleccionada.
     * Utilizado por el Servicio para buscar la entidad {@code Editorial} real mediante su repositorio.
     * @return Identificador único de la casa editorial proveniente del JSON.
     */
    public Integer getIdEditorial() { 
        return this.idEditorial; 
    }


    /**
     * Define el título del libro.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "titulo" desde el texto plano del JSON entrante.
     * @param titulo El título extraído del JSON del Frontend.
     */
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }

    /**
     * Define la sinopsis del libro.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "sinopsis" desde el texto plano del JSON entrante.
     * @param sinopsis La sinopsis extraída del JSON del Frontend.
     */
    public void setSinopsis(String sinopsis) { 
        this.sinopsis = sinopsis; 
    }

    /**
     * Define la ruta o URL de la imagen de portada.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "imagen" desde el texto plano del JSON entrante.
     * @param imagen La ubicación de la imagen extraída del JSON del Frontend.
     */
    public void setImagen(String imagen) { 
        this.imagen = imagen; 
    }

    /**
     * Define la cantidad total de páginas del libro.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "paginas" desde el texto plano del JSON entrante.
     * @param paginas El número de páginas extraído del JSON del Frontend.
     */
    public void setPaginas(Integer paginas) { 
        this.paginas = paginas; 
    }

    /**
     * Define el año de publicación del libro.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "ano" desde el texto plano del JSON entrante.
     * </p>
     * @param ano El año de publicación extraído del JSON del Frontend.
     */
    public void setAno(Integer ano) { 
        this.ano = ano; 
    }

    /**
     * Define el código internacional único (ISBN) del libro.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "isbn" desde el texto plano del JSON entrante.
     * @param isbn El código ISBN extraído del JSON del Frontend.
     */
    public void setIsbn(String isbn) { 
        this.isbn = isbn; 
    }

    /**
     * Define el identificador del género asociado.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "idGenero" desde el texto plano del JSON entrante.
     * @param idGenero Identificador del género extraído del JSON del Frontend.
     */
    public void setIdGenero(Integer idGenero) { 
        this.idGenero = idGenero; 
    }

    /**
     * Define el identificador del autor asociado.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "idAutor" desde el texto plano del JSON entrante.
     * @param idAutor Identificador del autor extraído del JSON del Frontend.
     */
    public void setIdAutor(Integer idAutor) { 
        this.idAutor = idAutor; 
    }

    /**
     * Define el identificador de la editorial asociada.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "idEditorial" desde el texto plano del JSON entrante.
     * @param idEditorial Identificador de la editorial extraído del JSON del Frontend.
     */
    public void setIdEditorial(Integer idEditorial) { 
        this.idEditorial = idEditorial; 
    }
}
