import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../componentes/navegacion/navbar/Navbar";
import { ContextoSesion } from "../contexto/Sesion";
import {obtenerPerfil, actualizarPerfil} from "../api/Perfil";
import {
    obtenerLibros,
    obtenerAutores,
    obtenerGeneros,
    buscarLibros,
    buscarAutores
} from "../api/Catalogo";
import { subirImagen } from "../api/Archivos";
import avatarDefecto from "../estilos/img/defecto/avatar.jpg";
import bannerDefecto from "../estilos/img/defecto/banner.png";

/**
 * Extrae el ID de usuario desde el payload del token JWT almacenado
 * en localStorage. Decodifica la parte central del token (Base64)
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
 * Resuelve la URL correcta de una imagen según su origen.
 * <p>
 * Si la ruta es nula o corresponde a una imagen por defecto del frontend
 * (rutas que comienzan con "/estilos/"), retorna la imagen de respaldo local.
 * Si la ruta apunta a una imagen subida por el usuario, le agrega el prefijo
 * del servidor backend para formar la URL completa de acceso.
 * </p>
 *
 * @param {string} ruta ruta de la imagen almacenada en la base de datos.
 * @param {string|null} imagenDefecto imagen local a mostrar si no hay ruta válida.
 * @returns {string} URL completa de la imagen a renderizar.
 */
function resolverUrlImagen(ruta, imagenDefecto) {
    if (!ruta) return imagenDefecto;
    if (ruta.startsWith("/estilos/")) return imagenDefecto;
    return `http://localhost:8080${ruta}`;
}

/**
 * Componente principal de la página de perfil del usuario autenticado.
 * <p>
 * Carga y muestra los datos del perfil del usuario que tiene sesión activa,
 * incluyendo banner, avatar, información personal, favoritos literarios
 * y actividad organizada en tabs. Permite editar todos los campos del perfil
 * incluyendo imágenes, y gestiona el cierre de sesión.
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

    // Catálogos
    const [libros, setLibros] = useState([]);
    const [autores, setAutores] = useState([]);
    const [generos, setGeneros] = useState([]);
    const [busquedaLibro, setBusquedaLibro] = useState("");
    const [busquedaAutor, setBusquedaAutor] = useState("");

    // Campos editables
    const [biografia, setBiografia] = useState("");
    const [avatar, setAvatar] = useState("");
    const [banner, setBanner] = useState("");
    const [idAutorFavorito, setIdAutorFavorito] = useState("");
    const [idGeneroFavorito, setIdGeneroFavorito] = useState("");
    const [idLibroFavorito, setIdLibroFavorito] = useState("");

    // Archivos
    const [archivoAvatar, setArchivoAvatar] = useState(null);
    const [archivoBanner, setArchivoBanner] = useState(null);
    const [previstaAvatar, setPrevistaAvatar] = useState("");
    const [previstaBanner, setPrevistaBanner] = useState("");

    useEffect(() => {
	/**
	 * Recupera el token de localStorage, extrae el ID del usuario
	 * y solicita al backend los datos completos del perfil.
	 * Inicializa los campos del formulario de edición con los valores actuales.
	 */
	const cargarPerfil = async () => {
            const tokenGuardado = localStorage.getItem("token");

            if (!tokenGuardado) {
		setError("No hay sesión activa");
		return;
            }

            const id = obtenerIdDesdeToken(tokenGuardado);

            if (!id) {
		setError("No se pudo obtener el ID del usuario");
		return;
            }

            setToken(tokenGuardado);
            setIdUsuario(id);

            try {
		const datosPerfil = await obtenerPerfil(id, tokenGuardado);

		setPerfil(datosPerfil);
		setBiografia(datosPerfil.biografia || "");
		setAvatar(datosPerfil.avatar || "");
		setBanner(datosPerfil.banner || "");
            } catch {
		setError("Error al cargar el perfil");
            }
	};

	cargarPerfil();
    }, []);
    
    useEffect(() => {
	if (!editando) return;
	/**
	 * Se ejecuta cuando el usuario abre el modo edición.
	 * Carga en paralelo la lista completa de libros, autores y géneros
	 * disponibles para poblar los selectores del formulario.
	 */
	const cargarCatalogos = async () => {
            try {
		const [
                    librosData,
                    autoresData,
                    generosData
		] = await Promise.all([
                    obtenerLibros(token),
                    obtenerAutores(token),
                    obtenerGeneros(token)
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
	 * Búsqueda de libros con debounce de 400ms.
	 * Se activa cuando el usuario escribe en el campo de búsqueda
	 * de libro favorito y la cadena tiene al menos 2 caracteres.
	 * Evita hacer una petición por cada tecla presionada.
	 */
	if (!editando || busquedaLibro.length < 2) return;

	const timeout = setTimeout(async () => {
            try {
		const librosEncontrados = await buscarLibros(
                    busquedaLibro,
                    token
		);

		setLibros(librosEncontrados);
            } catch {
		console.error("Error al buscar libros");
            }
	}, 400);

	return () => clearTimeout(timeout);
    }, [busquedaLibro]);
    
    useEffect(() => {
	/**
	 * Búsqueda de autores con debounce de 400ms.
	 * Se activa cuando el usuario escribe en el campo de búsqueda
	 * de autor favorito y la cadena tiene al menos 2 caracteres.
	 * Evita hacer una petición por cada tecla presionada.
	 */
	if (!editando || busquedaAutor.length < 2) return;

	const timeout = setTimeout(async () => {
            try {
		const autoresEncontrados = await buscarAutores(
                    busquedaAutor,
                    token
		);

		setAutores(autoresEncontrados);
            } catch {
		console.error("Error al buscar autores");
            }
	}, 400);

	return () => clearTimeout(timeout);
    }, [busquedaAutor]);

    /**
     * Maneja la selección de un archivo de imagen desde el explorador
     * de archivos del sistema. Genera una URL de previsualización local
     * usando URL.createObjectURL para mostrar la imagen antes de subirla.
     *
     * @param {Event} e evento de cambio del input de tipo file.
     * @param {string} tipo indica si la imagen es para "avatar" o "banner".
     */
    const manejarSeleccionImagen = (e, tipo) => {
        const archivo = e.target.files[0];
        if (!archivo) return;
        const urlPrevia = URL.createObjectURL(archivo);
        if (tipo === "avatar") { setArchivoAvatar(archivo); setPrevistaAvatar(urlPrevia); }
        else { setArchivoBanner(archivo); setPrevistaBanner(urlPrevia); }
    };

    /**
     * Orquesta el guardado de los cambios del perfil.
     * Si el usuario seleccionó imágenes nuevas, las sube primero al servidor
     * y obtiene sus URLs antes de enviar el resto de los datos.
     * Al finalizar limpia los archivos temporales y cierra el modo edición.
     */
    const guardarCambios = async () => {
	setGuardando(true);

	try {
            let urlAvatar = avatar;
            let urlBanner = banner;

	    if (archivoAvatar) urlAvatar = await subirImagen(archivoAvatar, token, "avatares");
	    if (archivoBanner) urlBanner = await subirImagen(archivoBanner, token, "banners");
	    
            const datos = {
		biografia,
		avatar: urlAvatar,
		banner: urlBanner,
		idAutorFavorito: idAutorFavorito
                    ? parseInt(idAutorFavorito)
                    : null,
		idGeneroFavorito: idGeneroFavorito
                    ? parseInt(idGeneroFavorito)
                    : null,
		idLibroFavorito: idLibroFavorito
                    ? parseInt(idLibroFavorito)
                    : null,
            };

            const perfilActualizado = await actualizarPerfil(
		idUsuario,
		datos,
		token
            );

            setPerfil(perfilActualizado);

            setArchivoAvatar(null);
            setArchivoBanner(null);

            setPrevistaAvatar("");
            setPrevistaBanner("");

            setEditando(false);

	} catch {
            setError("Error al guardar los cambios");
	} finally {
            setGuardando(false);
	}
    };

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

    const tabs = [
        { id: "likeadas", label: "Reseñas likeadas", count: perfil.resenasLikeadas?.length || 0 },
        { id: "calificadas", label: "Reseñas calificadas", count: perfil.resenasCalificadas?.length || 0 },
        { id: "libros", label: "Libros calificados", count: perfil.librosCalificados?.length || 0 },
        { id: "comentarios", label: "Comentarios likeados", count: perfil.comentariosLikeados?.length || 0 },
    ];

    /**
     * Cierra la sesión del usuario eliminando el token del contexto
     * y redirige a la página de inicio.
     */
    const manejarLogout = () => {
	cerrarSesion();
	navigate("/");
    };
    
    return (
	<div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo transition-colors duration-500">
	    <Navbar estaAutenticado={true} />
	    
	    {/* ── TARJETA CONTENEDOR ── */}
	    <div className="py-8 px-4">
		<div className="max-w-4xl mx-auto bg-white dark:bg-dark-borde rounded-2xl shadow-md overflow-hidden border border-gray-100 dark:border-white/5">

		    {/* ── BANNER dentro del contenedor ── */}
		    <div className="relative w-full h-48 md:h-56">
			<img
			    src={resolverUrlImagen(perfil.banner, bannerDefecto)}
			    alt="Banner"
			    className="w-full h-full object-cover"
			/>
			<div className="absolute inset-0 bg-gradient-to-t from-black/30 to-transparent" />
		    </div>

		    {/* ── CONTENIDO DEL PERFIL ── */}
		    <div className="px-6 md:px-10">

			{/* ── CABECERA ── */}
			<div className="relative flex flex-col md:flex-row md:items-end md:justify-between gap-4 pb-6 border-b border-gray-200 dark:border-dark-fondo">

			    {/* Avatar */}
	    <div className="relative -mt-16 md:-mt-20">
	    <img
	    src={resolverUrlImagen(perfil.avatar, avatarDefecto)}
	    alt="Avatar"
	    className="w-28 h-28 md:w-36 md:h-36 rounded-full object-cover border-4 border-white dark:border-dark-borde shadow-lg"
	/>
	</div>

	    {/* Botones — para editar y salir*/}
	    {!editando && (
	    <div className="flex gap-3 md:mb-2">
	    <button
	    onClick={() => setEditando(true)}
	    className="flex items-center gap-2 px-5 py-2 rounded-md border border-gray-300 dark:border-gray-600 text-navy-letter dark:text-gray-200 font-inter text-sm font-medium hover:bg-gray-100 dark:hover:bg-dark-borde transition-all duration-200 cursor-pointer"
	>
	    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
	    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
	</svg>
		  Editar perfil
	</button>
	
	    <button
	    onClick={manejarLogout}
	    className="flex items-center gap-2 px-5 py-2 rounded-md border border-red-400 text-red-500 font-inter text-sm font-medium hover:bg-red-50 dark:hover:bg-red-950/30 transition-all duration-200 cursor-pointer"
	>
	    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
	    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
	</svg>
		  Salir
	</button>
	</div>
	)}
	</div>

			{/* ── MODO EDICIÓN ── */}
			{editando ? (
			    <div className="py-8">
				<h2 className="font-cormorant text-2xl font-bold text-navy-letter dark:text-gray-100 mb-6">
															       Editar información
				</h2>

		    <div className="bg-white dark:bg-dark-borde rounded-2xl border border-gray-100 dark:border-white/5 shadow-sm p-6 md:p-8 flex flex-col gap-6">

			{/* Imágenes */}
			<div className="grid grid-cols-1 md:grid-cols-2 gap-6">

			    {/* Avatar */}
			    <div className="flex flex-col gap-3">
				<label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">
															    Avatar
				</label>
	    <img
		src={previstaAvatar || resolverUrlImagen(perfil.avatar, avatarDefecto)}
	        alt="Preview avatar"
	        className="w-24 h-24 rounded-full object-cover border-2 border-gray-200 dark:border-gray-600"
	    />
	    <label className="flex items-center gap-2 px-4 py-2 rounded-md border border-dashed border-gray-300 dark:border-gray-600 text-sm font-inter text-gray-500 dark:text-gray-400 hover:border-gold-button hover:text-gold-button transition-all cursor-pointer w-fit">
		<svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
		    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
		</svg>
			  Cambiar avatar
	        <input type="file" accept="image/*" className="hidden" onChange={e => manejarSeleccionImagen(e, "avatar")} />
	    </label>
	</div>

			    {/* Banner */}
			    <div className="flex flex-col gap-3">
				<label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">
															    Banner
				</label>
	    <img
		src={previstaBanner || resolverUrlImagen(perfil.banner, bannerDefecto)}
	        alt="Preview banner"
	        className="w-full h-24 rounded-lg object-cover border-2 border-gray-200 dark:border-gray-600"
	    />
	    <label className="flex items-center gap-2 px-4 py-2 rounded-md border border-dashed border-gray-300 dark:border-gray-600 text-sm font-inter text-gray-500 dark:text-gray-400 hover:border-gold-button hover:text-gold-button transition-all cursor-pointer w-fit">
		<svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
		    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
		</svg>
			  Cambiar banner
	        <input type="file" accept="image/*" className="hidden" onChange={e => manejarSeleccionImagen(e, "banner")} />
	    </label>
	</div>
			</div>

			{/* Biografía */}
			<div className="flex flex-col gap-2">
			    <label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">
															Biografía
			    </label>
			    <textarea
				rows={3}
				value={biografia}
				onChange={e => setBiografia(e.target.value)}
				placeholder="Cuéntanos sobre ti..."
				className="w-full px-4 py-3 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm resize-none focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
			    />
			</div>

			{/* Favoritos */}
			<div className="grid grid-cols-1 md:grid-cols-3 gap-4">

			    {/* Autor favorito */}
			    <div className="flex flex-col gap-2">
				<label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">
															    Autor favorito
				</label>
				<input
				    placeholder="Buscar autor..."
	    value={busquedaAutor}
	    onChange={e => setBusquedaAutor(e.target.value)}
	    className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
	/>
				<select
				    value={idAutorFavorito}
	    onChange={e => setIdAutorFavorito(e.target.value)}
	    className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
	>
	    <option value="">-- Selecciona --</option>
	    {autores.map(a => <option key={a.idAutor} value={a.idAutor}>{a.nombreAutor}</option>)}
	</select>
			    </div>

			    {/* Género favorito */}
			    <div className="flex flex-col gap-2">
				<label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">
															    Género favorito
				</label>
				<select
				    value={idGeneroFavorito}
	    onChange={e => setIdGeneroFavorito(e.target.value)}
	    className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all mt-8"
	>
	    <option value="">-- Selecciona --</option>
	    {generos.map(g => <option key={g.idGenero} value={g.idGenero}>{g.nombreGenero}</option>)}
	</select>
			    </div>

			    {/* Libro favorito */}
			    <div className="flex flex-col gap-2">
				<label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">
															    Libro favorito
				</label>
				<input
				    placeholder="Buscar libro..."
	    value={busquedaLibro}
	    onChange={e => setBusquedaLibro(e.target.value)}
	    className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
	/>
				<select
				    value={idLibroFavorito}
	    onChange={e => setIdLibroFavorito(e.target.value)}
	    className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
	>
	    <option value="">-- Selecciona --</option>
	    {libros.map(l => <option key={l.idLibro} value={l.idLibro}>{l.titulo}</option>)}
	</select>
			    </div>
			</div>

			{/* Botones */}
			<div className="flex gap-3 pt-2">
			    <button
				onClick={guardarCambios}
				disabled={guardando}
				className="px-6 py-2 rounded-md bg-gold-button hover:bg-gold-button-hover text-white font-inter font-bold text-sm transition-all duration-200 disabled:opacity-60 cursor-pointer"
			    >
				{guardando ? "Guardando..." : "Guardar cambios"}
			    </button>
			    <button
				onClick={() => setEditando(false)}
				className="px-6 py-2 rounded-md border border-gray-300 dark:border-gray-600 text-navy-letter dark:text-gray-200 font-inter font-medium text-sm hover:bg-gray-100 dark:hover:bg-dark-fondo transition-all duration-200 cursor-pointer"
			    >
	     Cancelar
			    </button>
			</div>
		    </div>
		</div>

	) : (

	    /* ── MODO VISTA ── */
	    <div className="py-6 flex flex-col gap-8">

		{/* Nombre y bio */}
		<div className="flex flex-col gap-1">
		    <h1 className="font-cormorant text-3xl md:text-4xl font-bold text-navy-letter dark:text-gray-100">
			{perfil.nombreUsuario}
	</h1>
	    <p className="font-inter text-sm text-gold-button">@{perfil.nombreUsuario}</p>
	    {perfil.biografia && (
		<p className="font-inter text-sm text-gray-500 dark:text-gray-400 mt-2 max-w-lg leading-relaxed">
		    {perfil.biografia}
		</p>
	    )}			   
	</div>

		{/* Favoritos */}
		<div className="flex flex-col gap-3">

		    {/* Autor y Género en fila */}
		    <div className="grid grid-cols-2 gap-3">
			<div className="bg-crema-fondo dark:bg-dark-fondo rounded-xl border border-gray-100 dark:border-white/5 p-4">
			    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider mb-1">Autor favorito</p>
			    <p className="font-cormorant text-lg font-bold text-gold-button">
				{perfil.autorFavorito !== "Ninguno"
				    ? perfil.autorFavorito
				    : <span className="text-gray-400 text-sm font-inter font-normal">No definido</span>}
	</p>
			</div>
	    <div className="bg-crema-fondo dark:bg-dark-fondo rounded-xl border border-gray-100 dark:border-white/5 p-4">
	    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider mb-1">Género favorito</p>
	    <p className="font-cormorant text-lg font-bold text-gold-button">
	    {perfil.generoFavorito !== "Ninguno"
	    ? perfil.generoFavorito
	    : <span className="text-gray-400 text-sm font-inter font-normal">No definido</span>}
	</p>
	</div>
	</div>

		    {/* Libro favorito */}
		    <div className="flex items-start gap-4">
			{/* Portada */}
			<div className="w-16 h-24 bg-gray-200 dark:bg-dark-fondo rounded-lg overflow-hidden flex-shrink-0">
			    {perfil.imagenLibroFavorito ? (
				<img
				    src={resolverUrlImagen(perfil.imagenLibroFavorito, null)}
				    alt={perfil.libroFavorito}
				    className="w-full h-full object-cover"
				/>
			    ) : (
				<div className="w-full h-full flex items-center justify-center">
				    <span className="text-2xl">📖</span>
				</div>
			    )}
			</div>

			{/* Título y autor */}
			<div className="flex flex-col justify-start gap-1">
			    <p className="font-cormorant text-xl font-bold text-navy-letter dark:text-gray-100 leading-tight">
				{perfil.libroFavorito}
			    </p>
			    {perfil.autorLibroFavorito && (
				<p className="font-inter text-sm text-gray-500 dark:text-gray-400">
				    {perfil.autorLibroFavorito}
				</p>
			    )}
			</div>
		    </div>
		</div>

		{/* ── ACTIVIDAD ── */}
		<div>
		    <h2 className="font-cormorant text-2xl font-bold text-navy-letter dark:text-gray-100 mb-4">
												       Tu actividad
	</h2>

	    {/* Tabs */}
	    <div className="flex gap-1 border-b border-gray-200 dark:border-dark-borde mb-6 overflow-x-auto">
		{tabs.map(tab => (
		    <button
			key={tab.id}
			onClick={() => setTabActiva(tab.id)}
			className={`px-4 py-3 font-inter text-sm font-medium whitespace-nowrap transition-all duration-200 border-b-2 cursor-pointer
                                            ${tabActiva === tab.id
                                                ? "border-gold-button text-gold-button"
                                                : "border-transparent text-gray-500 dark:text-gray-400 hover:text-navy-letter dark:hover:text-gray-200"
                                            }`}
		    >
			{tab.label}
			<span className={`ml-2 px-1.5 py-0.5 rounded-full text-xs
                                            ${tabActiva === tab.id
                                                ? "bg-gold-button/10 text-gold-button"
                                                : "bg-gray-100 dark:bg-dark-borde text-gray-400"
                                            }`}>
		            {tab.count}
			</span>
		    </button>
		))}
	    </div>

	    {/* Contenido de tabs */}

	    {/* Reseñas likeadas */}
	    {tabActiva === "likeadas" && (
		<div className="flex flex-col gap-3">
		    {perfil.resenasLikeadas?.length > 0 ? perfil.resenasLikeadas.map(r => (
			<div key={r.idResena} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-5 hover:shadow-md transition-shadow duration-200">
			    <div className="flex items-start justify-between gap-4">
				<div className="flex-1">
				    <p className="font-cormorant text-lg font-bold text-navy-letter dark:text-gray-100 mb-1">
					{r.tituloLibro}
				    </p>
				    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mb-3">
											    Reseña de <span className="text-gold-button">@{r.autorResena}</span> · {r.fechaLike}
		</p>
				    <p className="font-inter text-sm text-gray-600 dark:text-gray-300 leading-relaxed line-clamp-3">
					{r.textoResena}
		</p>
				</div>
				<span className="text-red-400 text-xl flex-shrink-0">♥</span>
			    </div>
			</div>
		    )) : (
			<MensajeVacio texto="Aún no has dado like a ninguna reseña" />
		)}
		</div>
	    )}

	    {/* Reseñas calificadas */}
	    {tabActiva === "calificadas" && (
		<div className="flex flex-col gap-3">
		    {perfil.resenasCalificadas?.length > 0 ? perfil.resenasCalificadas.map(r => (
			<div key={r.idResena} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-5 hover:shadow-md transition-shadow duration-200">
			    <div className="flex items-start justify-between gap-4">
				<div className="flex-1">
				    <p className="font-cormorant text-lg font-bold text-navy-letter dark:text-gray-100 mb-1">
					{r.tituloLibro}
				    </p>
				    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mb-3">
											    Reseña de <span className="text-gold-button">@{r.autorResena}</span> · {r.fechaCalificacion}
		</p>
				    <p className="font-inter text-sm text-gray-600 dark:text-gray-300 leading-relaxed line-clamp-3">
					{r.textoResena}
		</p>
				</div>
				<div className="flex items-center gap-1 bg-gold-button/10 px-3 py-1 rounded-full flex-shrink-0">
				    <span className="text-gold-button text-sm">★</span>
	            <span className="font-inter font-bold text-gold-button text-sm">{r.calificacion}</span>
		</div>
			    </div>
			</div>
		    )) : (
		        <MensajeVacio texto="Aún no has calificado ninguna reseña" />
		)}
		</div>
	    )}

	    {/* Libros calificados */}
	    {tabActiva === "libros" && (
		<div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
		    {perfil.librosCalificados?.length > 0 ? perfil.librosCalificados.map(l => (
			<div key={l.idLibro} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-4 flex gap-4 hover:shadow-md transition-shadow duration-200">
			    {l.imagen ? (
				<img
				    src={resolverUrlImagen(l.imagen, null)}
				    alt={l.titulo}
				    className="w-14 h-20 object-cover rounded-lg flex-shrink-0"
				/>
			    ) : (
				<div className="w-14 h-20 bg-gray-100 dark:bg-dark-fondo rounded-lg flex-shrink-0 flex items-center justify-center">
				    <span className="text-2xl">📖</span>
	</div>
	)}
			    <div className="flex flex-col justify-between flex-1">
				<div>
				    <p className="font-cormorant text-base font-bold text-navy-letter dark:text-gray-100 leading-tight">
					{l.titulo}
		</p>
	            <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mt-0.5">
			{l.autor}
		    </p>
		</div>
	    <div className="flex items-center gap-1 bg-gold-button/10 px-2 py-0.5 rounded-full w-fit">
		<span className="text-gold-button text-xs">★</span>
	        <span className="font-inter font-bold text-gold-button text-xs">{l.calificacion}/10</span>
	    </div>
	</div>
			</div>
		    )) : (
			<div className="col-span-2">
			    <MensajeVacio texto="Aún no has calificado ningún libro" />
	</div>
	)}
		</div>
	    )}

	    {/* Comentarios likeados */}
	    {tabActiva === "comentarios" && (
		<div className="flex flex-col gap-3">
		    {perfil.comentariosLikeados?.length > 0 ? perfil.comentariosLikeados.map(c => (
			<div key={c.idComentario} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-5 hover:shadow-md transition-shadow duration-200">
			    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mb-2">
													Comentario en <span className="font-semibold text-navy-letter dark:text-gray-300">{c.tituloLibro}</span> · por <span className="text-gold-button">@{c.autorComentario}</span> · {c.fechaLike}
			    </p>
			    <p className="font-inter text-sm text-gray-600 dark:text-gray-300 leading-relaxed">
				{c.texto}
	</p>
			</div>
		    )) : (
			<MensajeVacio texto="Aún no has dado like a ningún comentario" />
	)}
		</div>
	    )}
	</div>
	    </div>
	)}
		    </div>
		</div>
	    </div>
	</div>
    );
}

/**
 * Componente auxiliar que muestra un mensaje visual cuando
 * una sección de actividad no tiene contenido disponible.
 *
 * @param {string} texto mensaje descriptivo a mostrar al usuario.
 * @returns {JSX.Element} contenedor centrado con ícono y mensaje.
 */
function MensajeVacio({ texto }) {
    return (
	<div className="flex flex-col items-center justify-center py-16 gap-3">
	    <span className="text-4xl">📚</span>
	    <p className="font-inter text-sm text-gray-400 dark:text-gray-500">{texto}</p>
	</div>
    );
}

export default Perfil;
