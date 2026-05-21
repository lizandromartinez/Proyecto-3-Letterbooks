import { useEffect, useState } from "react";
import axios from "axios";
import avatarDefecto from "../estilos/img/defecto/avatar.jpg";
import bannerDefecto from "../estilos/img/defecto/banner.png";

/**
 * Extrae el ID de usuario desde el token JWT.
 */
function obtenerIdDesdeToken(token) {
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.id_usuario || null;
    } catch {
        return null;
    }
}

/**
 * Resuelve la URL correcta de una imagen según su origen.
 * Si es una ruta por defecto del frontend no le agrega el prefijo del backend.
 */
function resolverUrlImagen(ruta, imagenDefecto) {
    if (!ruta) return imagenDefecto;
    if (ruta.startsWith("/estilos/")) return imagenDefecto;
    return `http://localhost:8080${ruta}`;
}

/**
 * Componente de perfil del usuario.
 */
function Perfil() {
    const [perfil, setPerfil] = useState(null);
    const [error, setError] = useState(null);
    const [editando, setEditando] = useState(false);
    const [esPropietario, setEsPropietario] = useState(false);
    const [token, setToken] = useState(null);
    const [idUsuario, setIdUsuario] = useState(null);

    // Catálogos
    const [libros, setLibros] = useState([]);
    const [autores, setAutores] = useState([]);
    const [generos, setGeneros] = useState([]);

    // Búsquedas
    const [busquedaLibro, setBusquedaLibro] = useState("");
    const [busquedaAutor, setBusquedaAutor] = useState("");

    // Campos editables
    const [biografia, setBiografia] = useState("");
    const [idAutorFavorito, setIdAutorFavorito] = useState("");
    const [idGeneroFavorito, setIdGeneroFavorito] = useState("");
    const [idLibroFavorito, setIdLibroFavorito] = useState("");

    // Archivos e imágenes
    const [archivoAvatar, setArchivoAvatar] = useState(null);
    const [archivoBanner, setArchivoBanner] = useState(null);
    const [previstaAvatar, setPrevistaAvatar] = useState("");
    const [previstaBanner, setPrevistaBanner] = useState("");

    useEffect(() => {
        const obtenerPerfil = async () => {
            const tokenGuardado = localStorage.getItem("token");
            if (!tokenGuardado) { setError("No hay sesión activa"); return; }

            const id = obtenerIdDesdeToken(tokenGuardado);
            if (!id) { setError("No se pudo obtener el ID del usuario"); return; }

            setToken(tokenGuardado);
            setIdUsuario(id);
            setEsPropietario(true);

            try {
                const respuesta = await axios.get(
                    `http://localhost:8080/api/usuarios/${id}/perfil`,
                    { headers: { Authorization: `Bearer ${tokenGuardado}` } }
                );
                setPerfil(respuesta.data);
                setBiografia(respuesta.data.biografia || "");
            } catch (err) {
                setError("Error al cargar el perfil");
            }
        };
        obtenerPerfil();
    }, []);

    // Carga catálogos al abrir edición
    useEffect(() => {
        if (!editando) return;
        const cargarCatalogos = async () => {
            try {
                const config = { headers: { Authorization: `Bearer ${token}` } };
                const [resLibros, resAutores, resGeneros] = await Promise.all([
                    axios.get("http://localhost:8080/api/catalogo/libros", config),
                    axios.get("http://localhost:8080/api/catalogo/autores", config),
                    axios.get("http://localhost:8080/api/catalogo/generos", config)
                ]);
                setLibros(resLibros.data);
                setAutores(resAutores.data);
                setGeneros(resGeneros.data);
            } catch (err) {
                console.error("Error al cargar catálogos:", err);
            }
        };
        cargarCatalogos();
    }, [editando]);

    // Búsqueda de libros con debounce
    useEffect(() => {
        if (!editando || busquedaLibro.length < 2) return;
        const timeout = setTimeout(async () => {
            const res = await axios.get(
                `http://localhost:8080/api/catalogo/libros/buscar?titulo=${busquedaLibro}`,
                { headers: { Authorization: `Bearer ${token}` } }
            );
            setLibros(res.data);
        }, 400);
        return () => clearTimeout(timeout);
    }, [busquedaLibro]);

    // Búsqueda de autores con debounce
    useEffect(() => {
        if (!editando || busquedaAutor.length < 2) return;
        const timeout = setTimeout(async () => {
            const res = await axios.get(
                `http://localhost:8080/api/catalogo/autores/buscar?nombre=${busquedaAutor}`,
                { headers: { Authorization: `Bearer ${token}` } }
            );
            setAutores(res.data);
        }, 400);
        return () => clearTimeout(timeout);
    }, [busquedaAutor]);

    /**
     * Maneja la selección de imagen y genera previsualización local.
     */
    const manejarSeleccionImagen = (e, tipo) => {
        const archivo = e.target.files[0];
        if (!archivo) return;
        const urlPrevia = URL.createObjectURL(archivo);
        if (tipo === "avatar") {
            setArchivoAvatar(archivo);
            setPrevistaAvatar(urlPrevia);
        } else {
            setArchivoBanner(archivo);
            setPrevistaBanner(urlPrevia);
        }
    };

    /**
     * Sube una imagen al servidor y retorna su URL.
     */
    const subirImagen = async (archivo, tipo) => {
	const formData = new FormData();
	
	formData.append("archivo", archivo);
	
	const res = await axios.post(
            `http://localhost:8080/api/almacenamiento/imagen/${tipo}`,
            formData,
            {
		headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "multipart/form-data"
		}
            }
	);
	
	return res.data.url;
    };
    
    /**
     * Guarda los cambios subiendo imágenes si hay nuevas y actualizando el perfil.
     */
    const guardarCambios = async () => {
        try {
            let urlAvatar = perfil.avatar;
            let urlBanner = perfil.banner;

	    if (archivoAvatar) {
		urlAvatar = await subirImagen(archivoAvatar, "avatares");
	    }
	    
	    if (archivoBanner) {
		urlBanner = await subirImagen(archivoBanner, "banners");
	    }
	    
            const datos = {
                biografia,
                avatar: urlAvatar,
                banner: urlBanner,
                idAutorFavorito: idAutorFavorito ? parseInt(idAutorFavorito) : null,
                idGeneroFavorito: idGeneroFavorito ? parseInt(idGeneroFavorito) : null,
                idLibroFavorito: idLibroFavorito ? parseInt(idLibroFavorito) : null,
            };

            const respuesta = await axios.put(
                `http://localhost:8080/api/usuarios/${idUsuario}/perfil`,
                datos,
                { headers: { Authorization: `Bearer ${token}` } }
            );
            setPerfil(respuesta.data);
            setArchivoAvatar(null);
            setArchivoBanner(null);
            setPrevistaAvatar("");
            setPrevistaBanner("");
            setEditando(false);
        } catch (err) {
            setError("Error al guardar los cambios");
        }
    };

    if (error) return <p>{error}</p>;
    if (!perfil) return <p>Cargando perfil...</p>;

    return (
        <div style={{ maxWidth: "900px", margin: "0 auto", padding: "20px", fontFamily: "Arial" }}>

            {/* Banner */}
            <img
                src={resolverUrlImagen(perfil.banner, bannerDefecto)}
                alt="Banner"
                style={{ width: "100%", height: "220px", objectFit: "cover", borderRadius: "10px" }}
            />

            {/* Avatar */}
            <img
                src={resolverUrlImagen(perfil.avatar, avatarDefecto)}
                alt="Avatar"
                style={{
                    width: "150px", height: "150px", borderRadius: "50%",
                    marginTop: "-60px", border: "4px solid white", backgroundColor: "white"
                }}
            />

            {/* Nombre */}
            <h1 style={{ marginTop: "10px" }}>
                {perfil.nombreUsuario || "Sin nombre de usuario"}
            </h1>

            {/* Botón editar */}
            {esPropietario && !editando && (
                <button
                    style={{
                        padding: "10px 15px", marginTop: "10px", cursor: "pointer",
                        border: "1px solid gray", borderRadius: "5px", backgroundColor: "#f0f0f0"
                    }}
                    onClick={() => setEditando(true)}
                >
                    Editar perfil
                </button>
            )}

            {/* FORMULARIO DE EDICIÓN */}
            {editando ? (
                <div style={{
                    marginTop: "20px", padding: "20px",
                    border: "1px solid #ccc", borderRadius: "10px", backgroundColor: "#fafafa"
                }}>
                    <h2>Editar perfil</h2>

                    {/* Avatar */}
                    <label style={{ display: "block", marginTop: "15px", fontWeight: "bold" }}>
                        Avatar:
                    </label>
                    <img
                        src={previstaAvatar || resolverUrlImagen(perfil.avatar, avatarDefecto)}
                        alt="Preview avatar"
                        style={{
                            width: "100px", height: "100px", borderRadius: "50%",
                            marginTop: "10px", objectFit: "cover", display: "block"
                        }}
                    />
                    <input
                        type="file"
                        accept="image/*"
                        style={{ display: "block", marginTop: "10px" }}
                        onChange={e => manejarSeleccionImagen(e, "avatar")}
                    />

                    {/* Banner */}
                    <label style={{ display: "block", marginTop: "15px", fontWeight: "bold" }}>
                        Banner:
                    </label>
                    <img
                        src={previstaBanner || resolverUrlImagen(perfil.banner, bannerDefecto)}
                        alt="Preview banner"
                        style={{
                            width: "100%", height: "120px", objectFit: "cover",
                            marginTop: "10px", borderRadius: "8px", display: "block"
                        }}
                    />
                    <input
                        type="file"
                        accept="image/*"
                        style={{ display: "block", marginTop: "10px" }}
                        onChange={e => manejarSeleccionImagen(e, "banner")}
                    />

                    {/* Biografía */}
                    <label style={{ display: "block", marginTop: "15px", fontWeight: "bold" }}>
                        Biografía:
                    </label>
                    <textarea
                        style={{
                            width: "100%", padding: "10px", marginTop: "5px",
                            borderRadius: "5px", border: "1px solid #ccc"
                        }}
                        value={biografia}
                        onChange={e => setBiografia(e.target.value)}
                    />

                    <hr style={{ margin: "30px 0" }} />

                    {/* Autor favorito */}
                    <h3>Autor favorito</h3>
                    <input
                        placeholder="Buscar autor..."
                        style={{
                            width: "100%", padding: "10px", marginTop: "10px",
                            borderRadius: "5px", border: "1px solid #ccc"
                        }}
                        value={busquedaAutor}
                        onChange={e => setBusquedaAutor(e.target.value)}
                    />
                    <select
                        style={{
                            width: "100%", padding: "10px", marginTop: "10px",
                            borderRadius: "5px", border: "1px solid #ccc"
                        }}
                        value={idAutorFavorito}
                        onChange={e => setIdAutorFavorito(e.target.value)}
                    >
                        <option value="">-- Selecciona un autor --</option>
                        {autores.map(a => (
                            <option key={a.idAutor} value={a.idAutor}>{a.nombreAutor}</option>
                        ))}
                    </select>

                    {/* Género favorito */}
                    <h3 style={{ marginTop: "25px" }}>Género favorito</h3>
                    <select
                        style={{
                            width: "100%", padding: "10px", marginTop: "10px",
                            borderRadius: "5px", border: "1px solid #ccc"
                        }}
                        value={idGeneroFavorito}
                        onChange={e => setIdGeneroFavorito(e.target.value)}
                    >
                        <option value="">-- Selecciona un género --</option>
                        {generos.map(g => (
                            <option key={g.idGenero} value={g.idGenero}>{g.nombreGenero}</option>
                        ))}
                    </select>

                    {/* Libro favorito */}
                    <h3 style={{ marginTop: "25px" }}>Libro favorito</h3>
                    <input
                        placeholder="Buscar libro..."
                        style={{
                            width: "100%", padding: "10px", marginTop: "10px",
                            borderRadius: "5px", border: "1px solid #ccc"
                        }}
                        value={busquedaLibro}
                        onChange={e => setBusquedaLibro(e.target.value)}
                    />
                    <select
                        style={{
                            width: "100%", padding: "10px", marginTop: "10px",
                            borderRadius: "5px", border: "1px solid #ccc"
                        }}
                        value={idLibroFavorito}
                        onChange={e => setIdLibroFavorito(e.target.value)}
                    >
                        <option value="">-- Selecciona un libro --</option>
                        {libros.map(l => (
                            <option key={l.idLibro} value={l.idLibro}>{l.titulo}</option>
                        ))}
                    </select>

                    {/* Botones */}
                    <div style={{ marginTop: "25px" }}>
                        <button
                            style={{
                                padding: "10px 15px", marginRight: "10px", cursor: "pointer",
                                border: "1px solid gray", borderRadius: "5px", backgroundColor: "#f0f0f0"
                            }}
                            onClick={guardarCambios}
                        >
                            Guardar
                        </button>
                        <button
                            style={{
                                padding: "10px 15px", cursor: "pointer",
                                border: "1px solid gray", borderRadius: "5px", backgroundColor: "#f0f0f0"
                            }}
                            onClick={() => setEditando(false)}
                        >
                            Cancelar
                        </button>
                    </div>
                </div>
            ) : (
                <div style={{ marginTop: "20px" }}>
                    <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                        <h3>Biografía</h3>
                        <p>{perfil.biografia || "Sin biografía aún"}</p>
                    </div>
                    <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                        <h3>Libro favorito</h3>
                        <p>{perfil.libroFavorito || "No definido"}</p>
                    </div>
                    <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                        <h3>Autor favorito</h3>
                        <p>{perfil.autorFavorito || "No definido"}</p>
                    </div>
                    <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px" }}>
                        <h3>Género favorito</h3>
                        <p>{perfil.generoFavorito || "No definido"}</p>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Perfil;
