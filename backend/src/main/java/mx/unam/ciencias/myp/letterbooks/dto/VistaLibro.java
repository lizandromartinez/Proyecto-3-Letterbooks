package mx.unam.ciencias.myp.letterbooks.dto;

/**
 * Representa el modelo de datos optimizado para la pantalla de visualización de un libro.
 * Esta clase se utiliza para el flujo de salida de datos (Response). 
 * Su propósito es desacoplar las entidades de persistencia de Hibernate antes de enviarlas al Frontend.
 * Al "aplanar" el objeto (sustituyendo las entidades complejas por cadenas de texto simples).
 */
public class VistaLibro {

    private Integer idLibro;
    private String titulo;
    private String sinopsis;
    private String imagen;
    private Integer paginas;
    private Integer ano;
    private String isbn;
    private String nombreAutor;
    private String nombreEditorial;
    private String nombreGenero;
    private Double promedioDeCalificacion;
    private Integer totalReportes;

    /**
     * Constructor vacío por defecto.
     * Permite al Servicio instanciar la clase limpiamente antes de comenzar 
     * el llenado de los campos informativos.
     */
    public VistaLibro() {}

    /**
     * Obtiene el ID del libro.
     * Invocación Automática: Ejecutado por Jackson para generar la propiedad 
     * "idLibro" en el JSON final enviado al navegador del cliente.
     * @return El identificador único del libro en el sistema.
     */
    public Integer getIdLibro() { 
        return this.idLibro; 
    }

    /**
     * Obtiene el título del libro.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "titulo" en el JSON.
     * @return Cadena de texto con el título del libro.
     */
    public String getTitulo() { 
        return this.titulo; 
    }

    /**
     * Obtiene la sinopsis del libro.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "sinopsis" en el JSON.
     * @return Cadena de texto con la sinopsis.
     */
    public String getSinopsis() { 
        return this.sinopsis; 
    }

    /**
     * Obtiene la ruta de la portada del libro.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "imagen" en el JSON.
     * @return Cadena de texto con el enlace de la imagen.
     */
    public String getImagen() { 
        return this.imagen; 
    }

    /**
     * Obtiene el número de páginas.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "paginas" en el JSON.
     * @return Entero con la cantidad de páginas.
     */
    public Integer getPaginas() { 
        return this.paginas; 
    }

    /**
     * Obtiene el año de publicación.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "ano" en el JSON.
     * @return Entero con el año de publicación.
     */
    public Integer getAno() { 
        return this.ano; 
    }

    /**
     * Obtiene el código ISBN.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "isbn" en el JSON.
     * @return Cadena de texto con el código ISBN.
     */
    public String getIsbn() { 
        return this.isbn; 
    }

    /**
     * Obtiene el nombre textual del autor.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "nombreAutor" 
     * en el JSON, evitando enviar el objeto Autor relacional completo.
     * @return Cadena de texto con el nombre del autor.
     */
    public String getNombreAutor() { 
        return this.nombreAutor; 
    }

    /**
     * Obtiene el nombre textual de la casa editorial.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "nombreEditorial" en el JSON.
     * @return Cadena de texto con el nombre de la editorial.
     */
    public String getNombreEditorial() { 
        return this.nombreEditorial; 
    }

    /**
     * Obtiene el nombre textual del género literario.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "nombreGenero" en el JSON.
     * @return Cadena de texto con el nombre de la categoría o género.
     */
    public String getNombreGenero() { 
        return this.nombreGenero; 
    }

    /**
     * Obtiene el promedio de calificaciones calculado para el libro.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "promedioDeCalificacion" 
     * en el JSON, permitiendo a la interfaz renderizar las estrellas de puntuación.
     * @return Un valor flotante de doble precisión con el promedio de notas asignadas.
     */
    public Double getPromedioDeCalificacion() {
        return this.promedioDeCalificacion;
    }

    /**
     * Obtiene la cantidad total de reportes o quejas acumuladas por el libro.
     * Invocación Automática: Ejecutado por Jackson para escribir el campo "totalReportes" en el JSON. 
     * Requerido por la interfaz para auditorías de moderación o administración.
     * </p>
     * @return Un entero con el conteo acumulado de reportes.
     */
    public Integer getTotalReportes() {
        return this.totalReportes;
    }


    /**
     * Asigna el ID del libro.
     * Invocado manualmente en la capa de Servicio para transferir la llave primaria de la entidad al DTO.
     * @param idLibro El identificador único asignado por la base de datos.
     */
    public void setIdLibro(Integer idLibro) { 
        this.idLibro = idLibro; 
    }

    /**
     * Asigna el título del libro.
     * Invocado manualmente en la capa de Servicio para copiar el título desde la entidad recuperada.
     * @param titulo El nombre o título del libro.
     */
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }

    /**
     * Asigna la sinopsis del libro.
     * Invocado manualmente en la capa de Servicio para extraer el resumen del libro.
     * @param sinopsis El resumen descriptivo del libro.
     */
    public void setSinopsis(String sinopsis) { 
        this.sinopsis = sinopsis; 
    }

    /**
     * Asigna la ruta de la portada del libro.
     * Invocado manualmente en la capa de Servicio para ligar el recurso multimedia.
     * @param imagen El enlace o ubicación de la portada.
     */
    public void setImagen(String imagen) { 
        this.imagen = imagen; 
    }

    /**
     * Asigna la cantidad total de páginas.
     * Invocado manualmente en la capa de Servicio al armar la estructura de salida.
     * @param paginas El número total de páginas.
     */
    public void setPaginas(Integer paginas) { 
        this.paginas = paginas; 
    }

    /**
     * Asigna el año de publicación del libro.
     * Invocado manualmente en la capa de Servicio.
     * @param ano El año de edición o publicación cronológica.
     */
    public void setAno(Integer ano) { 
        this.ano = ano; 
    }

    /**
     * Asigna el código internacional único (ISBN).
     * Invocado manualmente en la capa de Servicio.
     * @param isbn El código identificador del libro.
     */
    public void setIsbn(String isbn) { 
        this.isbn = isbn; 
    }

    /**
     * Asigna el nombre limpio del autor.
     * Invocado en el Servicio haciendo un {@code libro.getAutor().getNombreAutor()} 
     * para aplanar la relación y extraer únicamente la cadena de texto requerida por la vista.
     * @param nombreAutor Nombre completo del autor asignado.
     */
    public void setNombreAutor(String nombreAutor) { 
        this.nombreAutor = nombreAutor; 
    }

    /**
     * Asigna el nombre limpio de la casa editora.
     * Invocado en el Servicio haciendo un {@code libro.getEditorial().getNombreEditorial()} 
     * para extraer el texto plano hacia la vista.
     * @param nombreEditorial Nombre de la editorial asignado.
     */
    public void setNombreEditorial(String nombreEditorial) { 
        this.nombreEditorial = nombreEditorial; 
    }

    /**
     * Asigna el nombre limpio del género literario.
     * Invocado en el Servicio haciendo un {@code libro.getGenero().getNombreGenero()} 
     * para aplanar la categoría hacia la vista.
     * @param nombreGenero Nombre de la categoría o género asignado.
     */
    public void setNombreGenero(String nombreGenero) { 
        this.nombreGenero = nombreGenero; 
    }

    /**
     * Asigna el promedio de calificación del libro al objeto de vista.
     * Invocado en el Servicio haciendo un {@code libro.getPromedioCalificacion()}.
     * @param promedioDeCalificacion Valor numérico con el promedio de evaluación.
     */
    public void setPromedioDeCalificacion(Double promedioDeCalificacion) {
        this.promedioDeCalificacion = promedioDeCalificacion;
    }

    /**
     * Asigna el conteo totalizado de reportes que posee el libro.
     * Invocado por el Servicio, haciendo un {@code libro.getReportes()}.
     * @param totalReportes Cantidad entera de quejas registradas.
     */
    public void setTotalReportes(Integer totalReportes) {
        this.totalReportes = totalReportes;
    }
}
