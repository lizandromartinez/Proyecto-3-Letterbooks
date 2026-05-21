import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import Navbar from "../componentes/navegacion/navbar/Navbar";
import avatarDefecto from "../estilos/img/defecto/avatar.jpg";
import bannerDefecto from "../estilos/img/defecto/banner.png";

/**
 * Resuelve la URL correcta de una imagen según su origen.
 */
function resolverUrlImagen(ruta, imagenDefecto) {
    if (!ruta) return imagenDefecto;
    if (ruta.startsWith("/estilos/")) return imagenDefecto;
    return `http://localhost:8080${ruta}`;
}

/**
 * Componente auxiliar para estados vacíos.
 */
function MensajeVacio({ texto }) {
    return (
        <div className="flex flex-col items-center justify-center py-16 gap-3">
            <span className="text-4xl">📚</span>
            <p className="font-inter text-sm text-gray-400 dark:text-gray-500">{texto}</p>
        </div>
    );
}

/**
 * Componente para ver el perfil público de cualquier usuario.
 */
function PerfilPublico() {
    const { nombreUsuario } = useParams();
    const [perfil, setPerfil] = useState(null);
    const [error, setError] = useState(null);
    const [tabActiva, setTabActiva] = useState("likeadas");

    // Detectar si hay sesión activa para el navbar
    const estaAutenticado = !!localStorage.getItem("token");

    useEffect(() => {
        const obtenerPerfil = async () => {
            try {
                const respuesta = await axios.get(
                    `http://localhost:8080/api/usuarios/perfil/${nombreUsuario}`
                );
                setPerfil(respuesta.data);
            } catch {
                setError("No se encontró el perfil");
            }
        };
        obtenerPerfil();
    }, [nombreUsuario]);

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
        { id: "likeadas",    label: "Reseñas likeadas",      count: perfil.resenasLikeadas?.length    || 0 },
        { id: "calificadas", label: "Reseñas calificadas",   count: perfil.resenasCalificadas?.length || 0 },
        { id: "libros",      label: "Libros calificados",    count: perfil.librosCalificados?.length  || 0 },
        { id: "comentarios", label: "Comentarios likeados",  count: perfil.comentariosLikeados?.length || 0 },
    ];

    return (
        <div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo transition-colors duration-300">
            <Navbar estaAutenticado={estaAutenticado} />

            {/* ── BANNER ── */}
            <div className="relative w-full h-52 md:h-64">
                <img
                    src={resolverUrlImagen(perfil.banner, bannerDefecto)}
                    alt="Banner"
                    className="w-full h-full object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/30 to-transparent" />
            </div>

            {/* ── CONTENEDOR PRINCIPAL ── */}
            <div className="max-w-4xl mx-auto px-4 md:px-8">

                {/* ── CABECERA DEL PERFIL ── */}
                <div className="relative flex flex-col md:flex-row md:items-end md:justify-between gap-4 pb-6 border-b border-gray-200 dark:border-dark-borde">

                    {/* Avatar */}
                    <div className="relative -mt-16 md:-mt-20">
                        <img
                            src={resolverUrlImagen(perfil.avatar, avatarDefecto)}
                            alt="Avatar"
                            className="w-28 h-28 md:w-36 md:h-36 rounded-full object-cover border-4 border-crema-fondo dark:border-dark-fondo shadow-lg"
                        />
                    </div>

                    {/* Sin botones — perfil ajeno, solo lectura */}
                </div>

                {/* ── MODO VISTA ── */}
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
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">

                        {/* Autor y Género */}
                        <div className="grid grid-cols-2 gap-4">
                            <div className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-4">
                                <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider mb-1">Autor favorito</p>
                                <p className="font-cormorant text-lg font-bold text-gold-button">
                                    {perfil.autorFavorito && perfil.autorFavorito !== "Ninguno"
                                        ? perfil.autorFavorito
                                        : <span className="text-gray-400 text-sm font-inter font-normal">No definido</span>}
                                </p>
                            </div>
                            <div className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-4">
                                <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider mb-1">Género favorito</p>
                                <p className="font-cormorant text-lg font-bold text-gold-button">
                                    {perfil.generoFavorito && perfil.generoFavorito !== "Ninguno"
                                        ? perfil.generoFavorito
                                        : <span className="text-gray-400 text-sm font-inter font-normal">No definido</span>}
                                </p>
                            </div>
                        </div>

                        {/* Libro favorito */}
                        <div className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-4">
                            <div className="flex items-center gap-2 mb-2">
                                <span className="text-gold-button">♥</span>
                                <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider">Libro favorito</p>
                            </div>
                            {perfil.libroFavorito && perfil.libroFavorito !== "Ninguno" ? (
                                <p className="font-cormorant text-lg font-bold text-navy-letter dark:text-gray-100">
                                    {perfil.libroFavorito}
                                </p>
                            ) : (
                                <p className="text-gray-400 font-inter text-sm">No definido</p>
                            )}
                        </div>
                    </div>

                    {/* ── ACTIVIDAD ── */}
                    <div>
                        <h2 className="font-cormorant text-2xl font-bold text-navy-letter dark:text-gray-100 mb-4">
                            Actividad
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
                                )) : <MensajeVacio texto="Aún no ha dado like a ninguna reseña" />}
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
                                )) : <MensajeVacio texto="Aún no ha calificado ninguna reseña" />}
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
                                        <MensajeVacio texto="Aún no ha calificado ningún libro" />
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
                                )) : <MensajeVacio texto="Aún no ha dado like a ningún comentario" />}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PerfilPublico;
