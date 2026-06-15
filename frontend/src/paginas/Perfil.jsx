import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../componentes/navegacion/navbar/Navbar";
import { ContextoSesion } from "../contexto/Sesion";
import { obtenerPerfil, actualizarPerfil } from "../api/Perfil";
import { obtenerLibros, obtenerAutores, obtenerGeneros, buscarLibros, buscarAutores } from "../api/Catalogo";
import { subirImagen } from "../api/Archivos";
import BannerPerfil from "../componentes/perfil/BannerPerfil";
import InfoPerfil from "../componentes/perfil/InfoPerfil";
import FavoritosPerfil from "../componentes/perfil/FavoritosPerfil";
import ActividadPerfil from "../componentes/perfil/ActividadPerfil";
import FormularioEditarPerfil from "../componentes/perfil/FormularioEditarPerfil";

/**
 * Extrae el ID de usuario desde el payload del token JWT almacenado
 * en localStorage. Decodifica la parte central del token
 * y retorna el campo id_usuario si existe.
 *
 * @param {string} token token JWT en formato Bearer.
 * @returns {number|null} ID del usuario o null si el token es inválido.
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
 * Componente principal de la página de perfil del usuario autenticado.
 * <p>
 * Gestiona toda la lógica de carga, edición y guardado del perfil.
 * La presentación visual está delegada en subcomponentes especializados.
 * </p>
 *
 * @component
 * @returns {JSX.Element} vista completa del perfil propio con opciones de edición.
 */
function Perfil() {
    const navigate = useNavigate();
    const { cerrarSesion } = useContext(ContextoSesion);

    const [perfil, setPerfil] = useState(null);
    const [error, setError] = useState(null);
    const [editando, setEditando] = useState(false);
    const [token, setToken] = useState(null);
    const [idUsuario, setIdUsuario] = useState(null);
    const [guardando, setGuardando] = useState(false);
    const [tabActiva, setTabActiva] = useState("likeadas");

    const [libros, setLibros] = useState([]);
    const [autores, setAutores] = useState([]);
    const [generos, setGeneros] = useState([]);
    const [busquedaLibro, setBusquedaLibro] = useState("");
    const [busquedaAutor, setBusquedaAutor] = useState("");

    const [biografia, setBiografia] = useState("");
    const [avatar, setAvatar] = useState("");
    const [banner, setBanner] = useState("");
    const [idAutorFavorito, setIdAutorFavorito] = useState("");
    const [idGeneroFavorito, setIdGeneroFavorito] = useState("");
    const [idLibroFavorito, setIdLibroFavorito] = useState("");
    const [archivoAvatar, setArchivoAvatar] = useState(null);
    const [archivoBanner, setArchivoBanner] = useState(null);
    const [previstaAvatar, setPrevistaAvatar] = useState("");
    const [previstaBanner, setPrevistaBanner] = useState("");

    useEffect(() => {
        /**
         * Recupera el token de localStorage, extrae el ID del usuario
         * y solicita al backend los datos completos del perfil.
         */
        const cargarPerfil = async () => {
            const tokenGuardado = localStorage.getItem("token");
            if (!tokenGuardado) { setError("No hay sesión activa"); return; }
            const id = obtenerIdDesdeToken(tokenGuardado);
            if (!id) { setError("No se pudo obtener el ID del usuario"); return; }
            setToken(tokenGuardado);
            setIdUsuario(id);
            try {
                const datosPerfil = await obtenerPerfil(id, tokenGuardado);
                setPerfil(datosPerfil);
                setBiografia(datosPerfil.biografia || "");
                setAvatar(datosPerfil.avatar || null);
                setBanner(datosPerfil.banner || null);
                setIdAutorFavorito(datosPerfil.idAutor ? String(datosPerfil.idAutor) : "");
                setIdGeneroFavorito(datosPerfil.idGenero ? String(datosPerfil.idGenero) : "");
                setIdLibroFavorito(datosPerfil.idLibro ? String(datosPerfil.idLibro) : "");
            } catch {
                setError("Error al cargar el perfil");
            }
        };
        cargarPerfil();
    }, []);

    useEffect(() => {
        if (!editando) return;
        /**
         * Carga en paralelo libros, autores y géneros para los selectores del formulario.
         */
        const cargarCatalogos = async () => {
            try {
                const [librosData, autoresData, generosData] = await Promise.all([
                    obtenerLibros(token), obtenerAutores(token), obtenerGeneros(token)
                ]);
                setLibros(librosData);
                setAutores(autoresData);
                setGeneros(generosData);
            } catch {
                console.error("Error al cargar catálogos");
            }
        };
        cargarCatalogos();
    }, [editando]);

    useEffect(() => {
        /**
         * Búsqueda de libros con tiempo de espera de 400ms.
         */
        if (!editando || busquedaLibro.length < 2) return;
        const timeout = setTimeout(async () => {
            try {
                setLibros(await buscarLibros(busquedaLibro, token));
            } catch {
                console.error("Error al buscar libros");
            }
        }, 400);
        return () => clearTimeout(timeout);
    }, [busquedaLibro]);

    useEffect(() => {
        /**
         * Búsqueda de autores con tiempo de espera de 400ms.
         */
        if (!editando || busquedaAutor.length < 2) return;
        const timeout = setTimeout(async () => {
            try {
                setAutores(await buscarAutores(busquedaAutor, token));
            } catch {
                console.error("Error al buscar autores");
            }
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
        if (tipo === "avatar") { setArchivoAvatar(archivo); setPrevistaAvatar(urlPrevia); }
        else { setArchivoBanner(archivo); setPrevistaBanner(urlPrevia); }
    };

    /**
     * Gestiona el guardado subiendo imágenes nuevas si las hay.
     */
    const guardarCambios = async () => {
        setGuardando(true);
        try {
            let urlAvatar = avatar;
            let urlBanner = banner;
            if (archivoAvatar) urlAvatar = await subirImagen(archivoAvatar, token, "avatares");
            if (archivoBanner) urlBanner = await subirImagen(archivoBanner, token, "banners");
            const datos = {
                biografia, avatar: urlAvatar || null, banner: urlBanner || null,
                idAutorFavorito: idAutorFavorito ? parseInt(idAutorFavorito) : null,
                idGeneroFavorito: idGeneroFavorito ? parseInt(idGeneroFavorito) : null,
                idLibroFavorito: idLibroFavorito ? parseInt(idLibroFavorito) : null,
            };
            setPerfil(await actualizarPerfil(idUsuario, datos, token));
            setArchivoAvatar(null); setArchivoBanner(null);
            setPrevistaAvatar(""); setPrevistaBanner("");
            setEditando(false);
        } catch {
            setError("Error al guardar los cambios");
        } finally {
            setGuardando(false);
        }
    };

    /**
     * Cierra la sesión y redirige al inicio.
     */
    const manejarLogout = () => { cerrarSesion(); navigate("/"); };

    if (error) return (
        <div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo flex items-center justify-center">
            <p className="text-gray-500 dark:text-gray-400 font-inter">{error}</p>
        </div>
    );
    if (!perfil) return (
        <div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo flex items-center justify-center">
            <p className="text-gray-500 dark:text-gray-400 font-inter">Cargando perfil...</p>
        </div>
    );

    const botones = (
        <>
            <button onClick={() => setEditando(true)}
                className="flex items-center gap-2 px-5 py-2 rounded-md border border-gray-300 dark:border-gray-600 text-navy-letter dark:text-gray-200 font-inter text-sm font-medium hover:bg-gray-100 dark:hover:bg-dark-borde transition-all duration-200 cursor-pointer">
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                </svg>
			  Editar perfil
            </button>
            <button onClick={manejarLogout}
                className="flex items-center gap-2 px-5 py-2 rounded-md border border-red-400 text-red-500 font-inter text-sm font-medium hover:bg-red-50 dark:hover:bg-red-950/30 transition-all duration-200 cursor-pointer">
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                </svg>
			  Salir
            </button>
        </>
    );

    return (
        <div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo transition-colors duration-500">
            <Navbar estaAutenticado={true} avatarUrl={perfil?.avatar} />
            <div className="py-8 px-4">
                <div className="max-w-4xl mx-auto bg-white dark:bg-dark-borde rounded-2xl shadow-md overflow-hidden border border-gray-100 dark:border-white/5">
                    <BannerPerfil perfil={perfil} botones={!editando ? botones : null} />
                    <div className="px-6 md:px-10">
                        {editando ? (
                            <FormularioEditarPerfil
                                perfil={perfil}
                                campos={{ biografia, avatar, banner, busquedaAutor, busquedaLibro, idAutorFavorito, idGeneroFavorito, idLibroFavorito, previstaAvatar, previstaBanner }}
                                setters={{ setBiografia, setBusquedaAutor, setBusquedaLibro, setIdAutorFavorito, setIdGeneroFavorito, setIdLibroFavorito, manejarSeleccionImagen }}
                                autores={autores}
                                generos={generos}
                                libros={libros}
                                guardando={guardando}
                                onGuardar={guardarCambios}
                                onCancelar={() => setEditando(false)}
                            />
                        ) : (
                            <div className="py-6 flex flex-col gap-8">
                                <InfoPerfil perfil={perfil} />
                                <FavoritosPerfil perfil={perfil} />
                                <ActividadPerfil
                                    perfil={perfil}
                                    tabActiva={tabActiva}
                                    setTabActiva={setTabActiva}
                                    tituloSeccion="Tu actividad"
                                />
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Perfil;
