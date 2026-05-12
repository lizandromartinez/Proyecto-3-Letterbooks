# Convenciones del Proyecto - Letterbooks

Este documento guía nuestra forma de trabajar para que el código sea legible y no tengamos conflictos en Git.

## Manejo de Git (Flujo de Trabajo)
* **main**: Solo versiones finales y estables para entrega.
* **develop**: Rama de integración. Aquí se junta el trabajo de todos antes de hacer merge a main.
* **feature/[nombre]**: Para cada funcionalidad nueva (ej: `feature/landing-page`).
* **docs/[nombre]**: Para agregar documentación nueva (ej: `docs/convenciones`).
* **db/[nombre]**: Para agregar cambios a la base de datos (ej: `db/agregar-registros`).
* **fix/[error]**: Para corregir errores específicos (ej: `feature/error-registro`).

### Mensajes de Commit
Usaremos un formato descriptivo sencillo:
- `feat:` Para cosas nuevas.
- `fix:` Para corregir errores.
- `docs:` Para cambios en el README, este archivo o documentación de código.
- `style:` Para diseño (CSS) o formato de código.
- `refactor:` Mejora de código que no añade funciones ni arregla bugs.

*Ejemplo:* `feat: crear formulario de registro en react`

---

## Convenciones de Código (Backend - Java y SpringBoot)
* **Idioma:** Español (clases, variables, comentarios).
* **Clases:** `PascalCase` (Ej: `ControladorLibro.java`).
* **Métodos y Variables:** `camelCase` (Ej: `obtenerPromedio()`).
* **Indentación:** 4 espacios (no tabs).
- **Paquetes:** Todo bajo `mx.unam.ciencias.myp.letterbooks`.
* **Documentación:** Usar **Javadoc** en todas las clases públicas y métodos lógicos.
    - Debe incluir `@param`, `@return` y `@throws` cuando aplique.
    - Ejemplo:
      /**
       * Registra un nuevo libro en el sistema.
       * @param libro Objeto con la información del libro.
       * @return El ID del libro generado.
       */

---

## Convenciones de React (Frontend - JavaScript y React)
* **Componentes:** `PascalCase` (Ej: `VistaLibro.jsx`).
* **Funciones:** `camelCase` (Ej: `manejarClick()`).
* **CSS:** Usaremos archivos `.css` normales por cada componente grande.
* **Organización:**
    - `vista/src/componentes`: Piezas pequeñas reutilizables (botones, barra de navegación, etc...).
    - `vista/src/paginas`: Vistas completas (Landing, Perfil, Registro, etc...).
* **Documentación:** Usar **JSDoc** para explicar componentes y funciones complejas.
    - Ejemplo:
      /**
       * Componente que renderiza la tarjeta de un libro.
       * @param {Object} props - Propiedades del componente.
       * @param {string} props.titulo - Título del libro.
       */
      const TarjetaLibro = ({ titulo }) => { ... }

---

## Base de Datos (MariaDB)
* **Tablas:** Singular y Primera letra mayúscula (Ej: `Usuario`, `Libro`).
* **Llaves Primarias:** `id_nombretabla` (Ej: `id_usuario`).
- **Fechas:** Siempre como texto `YYYY-MM-DD`.
* **Comandos SQL:** Escribir palabras reservadas en MAYÚSCULAS (Ej: `SELECT`).

---

## Reglas del Equipo
1. **No subir código que no compile**: Antes de hacer `push` asegúrate de que el proyecto corre.
2. **Subir código documentado**: Antes de hacer `push` asegúrate de que el codigo que agregaste está documentado.
3. **Revisión**: Antes de hacer merge de una `feature` a `develop` alguien más debe revisar el código.
4. **Comentarios**: Explicar el "por qué" de lógicas complejas, no solo el "qué".
