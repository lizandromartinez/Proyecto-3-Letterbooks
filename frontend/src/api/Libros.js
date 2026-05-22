const URL_BASE = 'http://localhost:8080/api';

/**
 * Obtiene todos los libros disponibles.
 */
export async function obtenerTodosLosLibros(token) {
    const respuesta = await fetch(`${URL_BASE}/catalogo/libros`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (!respuesta.ok) {
        throw new Error('Error al obtener los libros');
    }
    return await respuesta.json();
}

/**
 * Obtiene todos los autores.
 */
export async function obtenerAutores(token) {
    const respuesta = await fetch(`${URL_BASE}/catalogo/autores`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (!respuesta.ok) {
        throw new Error('Error al obtener los autores');
    }
    return await respuesta.json();
}

/**
 * Obtiene todos los géneros.
 */
export async function obtenerGeneros(token) {
    const respuesta = await fetch(`${URL_BASE}/catalogo/generos`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (!respuesta.ok) {
        throw new Error('Error al obtener los géneros');
    }
    return await respuesta.json();
}

/**
 * Obtiene todas las editoriales.
 */
export async function obtenerEditoriales(token) {
    const respuesta = await fetch(`${URL_BASE}/catalogo/editoriales`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (!respuesta.ok) {
        throw new Error('Error al obtener las editoriales');
    }
    return await respuesta.json();
}

/**
 * Sube una imagen de portada al servidor de almacenamiento.
 */
export async function subirPortada(archivo, token) {
    const formData = new FormData();
    formData.append('archivo', archivo);

    const respuesta = await fetch(`${URL_BASE}/almacenamiento/imagen/portadas`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: formData
    });

    const datos = await respuesta.json();
    if (!respuesta.ok) {
        throw new Error(datos.error || 'Error al subir la portada');
    }
    return datos.url;
}

/**
 * Registra un nuevo libro.
 */
export async function registrarLibro(datosLibro, token) {
    const respuesta = await fetch(`${URL_BASE}/libros`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(datosLibro)
    });

    const texto = await respuesta.text();
    let datos;
    try {
        datos = JSON.parse(texto);
    } catch {
        datos = texto;
    }

    if (!respuesta.ok) {
        throw datos;
    }
    return datos;
}

/**
 * Obtiene los detalles de un libro por su ID.
 */
export async function obtenerLibroPorId(idLibro, token) {
    const respuesta = await fetch(`${URL_BASE}/libros/${idLibro}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (!respuesta.ok) {
        throw new Error('Error al obtener los detalles del libro');
    }
    return await respuesta.json();
}

/**
 * Crea un nuevo autor.
 */
export async function crearAutor(nombreAutor, token) {
    const respuesta = await fetch(`${URL_BASE}/catalogo/autores`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ nombreAutor })
    });
    if (!respuesta.ok) {
        throw new Error('Error al crear el autor');
    }
    return await respuesta.json();
}

/**
 * Crea un nuevo género.
 */
export async function crearGenero(nombreGenero, token) {
    const respuesta = await fetch(`${URL_BASE}/catalogo/generos`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ nombreGenero })
    });
    if (!respuesta.ok) {
        throw new Error('Error al crear el género');
    }
    return await respuesta.json();
}

/**
 * Crea una nueva editorial.
 */
export async function crearEditorial(nombreEditorial, token) {
    const respuesta = await fetch(`${URL_BASE}/catalogo/editoriales`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ nombreEditorial })
    });
    if (!respuesta.ok) {
        throw new Error('Error al crear la editorial');
    }
    return await respuesta.json();
}
