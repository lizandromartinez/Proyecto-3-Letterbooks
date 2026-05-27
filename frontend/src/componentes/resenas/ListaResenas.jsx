import React, { useState, useEffect, useContext } from 'react';
import { obtenerResenas, eliminarResena } from '../../api/Resenas';
import { ContextoSesion } from '../../contexto/Sesion';
import { obtenerUsuarioDelToken } from '../../utilidades/DecodificadorToken';
import { obtenerPerfilPublico } from '../../api/Perfil';
import FormularioResena from './FormularioResena';
import Cita from '../citas/Cita'; 
import avatarDefecto from '../../estilos/img/defecto/avatar.jpg';
import likeIcon from '../../estilos/img/iconos/like.png';
import './ListaResenas.css';

/**
 * Subcomponente para renderizar la tarjeta individual de una reseña.
 * Gestiona de forma local la consulta del avatar para no sobrecargar el endpoint principal.
 */
function TarjetaResena({ resena, usuarioActual, onEditar, onEliminar }) {
    const [avatarUrl, setAvatarUrl] = useState(avatarDefecto);

    useEffect(() => {
        if (resena.usuario?.nombreUsuario) {
            obtenerPerfilPublico(resena.usuario.nombreUsuario)
                .then((perfil) => {
                    if (perfil?.avatar) {
                        setAvatarUrl(`http://localhost:8080${perfil.avatar}`);
                    }
                })
                .catch(() => {});
        }
    }, [resena.usuario?.nombreUsuario]);

    const formatearFecha = (fechaIso) => {
        if (!fechaIso) return '';
        const fecha = new Date(fechaIso);
        if (Number.isNaN(fecha.getTime())) return fechaIso;
        return fecha.toLocaleDateString('es-ES', { day: 'numeric', month: 'long' });
    };

    return (
        <div className="bg-white dark:bg-dark-borde p-6 sm:p-8 rounded-3xl border border-gray-100 dark:border-white/10 shadow-sm hover:shadow-md transition-all duration-300 max-w-3xl mx-auto mb-6 w-full text-left">
            {/* Cabecera del usuario y fecha */}
            <div className="flex justify-between items-start mb-6">
                <div className="flex items-center gap-4">
                    <img 
                        src={avatarUrl} 
                        alt={resena.usuario?.nombreUsuario} 
                        className="w-12 h-12 rounded-full border-2 border-gold-button/20 object-cover" 
                    />
                    <div className="text-left">
                        <h4 className="font-bold text-navy-letter dark:text-white leading-tight">
                            {resena.usuario?.nombreUsuario}
                        </h4>
                        <p className="text-gray-400 text-sm">@{resena.usuario?.nombreUsuario}</p>
                    </div>
                </div>
                <span className="text-gray-400 text-xs font-inter">{formatearFecha(resena.fechaPublicacion)}</span>
            </div>

            {/* Texto de opinión */}
            <p className="text-black dark:text-white text-left mb-6 font-inter leading-relaxed whitespace-pre-line">
                {resena.textoResena}
            </p>

            {/* Citas literarias */}
            {resena.citas && resena.citas.length > 0 && (
                <div className="space-y-4 mb-6">
                    {resena.citas.map((cita) => (
                        <Cita key={cita.idCita} cita={cita} />
                    ))}
                </div>
            )}

            {/* Pie con likes, calificación y acciones de autoría */}
            <div className="flex flex-wrap items-center justify-between gap-4 pt-4 border-t border-gray-50 dark:border-white/5">
                <div className="flex items-center gap-6">
                    <div className="flex items-center gap-1.5 text-gray-400">
                        <img
                            src={likeIcon} 
                            alt="Me gusta" 
                            className="h-5 w-auto dark:invert dark:brightness-200"
                        />
                        <span className="text-xs font-bold">{resena.likes || 0}</span>
                    </div>

                    <div className="flex items-center gap-2">
                        <span className="text-xs text-gray-400 font-inter font-medium">
                            Calificación del libro:
                        </span>
                        <div className="flex text-gold-button text-xs tracking-wider">
                            {"★".repeat(resena.calificacionLibro)}{"☆".repeat(5 - resena.calificacionLibro)}
                        </div>
                    </div>
                </div>

                {/* Controles de edición y eliminación */}
                {usuarioActual === resena.usuario?.nombreUsuario && (
                    <div className="flex gap-2">
                        <button 
                            className="px-3.5 py-1 text-xs font-medium rounded-full bg-navy-button/10 text-navy-letter dark:bg-white/10 dark:text-white hover:bg-navy-button/20 dark:hover:bg-white/20 transition-colors"
                            onClick={() => onEditar(resena)}
                        >
                            Editar
                        </button>
                        <button 
                            className="px-3.5 py-1 text-xs font-medium rounded-full bg-red-500/10 text-red-500 hover:bg-red-500/20 transition-colors"
                            onClick={() => onEliminar(resena.idResena)}
                        >
                            Eliminar
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
}

/**
 * Componente principal que gestiona el listado de opiniones.
 */
function ListaResenas({ idLibro, alCambiarResenas, alResenasActualizadas }) {
    const { token } = useContext(ContextoSesion);
    const [resenas, setResenas] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [errorCarga, setErrorCarga] = useState(null);
    const [resenaEditando, setResenaEditando] = useState(null);

    const usuarioActual = obtenerUsuarioDelToken(token);
    const yaReseno = resenas.some((r) => r.usuario?.nombreUsuario === usuarioActual);

    const cargarResenas = async (refrescarLibro = false) => {
        try {
            setCargando(true);
            setErrorCarga(null);
            const datos = await obtenerResenas(idLibro, token);
            setResenas(datos);
            if (alResenasActualizadas) {
                alResenasActualizadas(datos);
            }
            if (refrescarLibro && alCambiarResenas) {
                alCambiarResenas();
            }
        } catch (error) {
            console.error("Error al cargar reseñas:", error);
            setErrorCarga("No se pudieron cargar las reseñas.");
        } finally {
            setCargando(false);
        }
    };

    useEffect(() => {
        if (idLibro) {
            cargarResenas();
        }
    }, [idLibro, token]);

    const manejarEliminacion = async (idResena) => {
        if (window.confirm("¿Estás seguro de eliminar esta reseña?")) {
            try {
                await eliminarResena(idResena, token);
                cargarResenas(true);
            } catch (error) {
                alert("No se pudo eliminar la reseña.");
            }
        }
    };

    if (cargando) return <p className="mensaje-cargando">Cargando opiniones...</p>;

    return (
        <div className="contenedor-lista-resenas">
            <h2 className="titulo-seccion">Opiniones de Lectores</h2>
            
            {/* Formulario principal para nueva reseña */}
            {token && !resenaEditando && !yaReseno && (
                <FormularioResena 
                    idLibro={idLibro} 
                    alCompletar={() => cargarResenas(true)} 
                />
            )}

            {errorCarga && <p className="mensaje-error-carga">{errorCarga}</p>}

            <div className="listado-tarjetas w-full flex flex-col items-center">
                {resenas.length === 0 && !errorCarga ? (
                    <p className="mensaje-vacio text-gray-500 dark:text-gray-400 italic">Sé el primero en opinar sobre este libro.</p>
                ) : (
                    resenas.map((resena) => (
                        resenaEditando?.idResena === resena.idResena ? (
                            <div key={resena.idResena} className="w-full max-w-3xl mb-6">
                                <FormularioResena 
                                    idLibro={idLibro}
                                    resenaAEditar={resenaEditando}
                                    alCompletar={() => {
                                        setResenaEditando(null);
                                        cargarResenas(true);
                                    }}
                                    alCancelar={() => setResenaEditando(null)}
                                />
                            </div>
                        ) : (
                            <TarjetaResena 
                                key={resena.idResena}
                                resena={resena}
                                usuarioActual={usuarioActual}
                                onEditar={setResenaEditando}
                                onEliminar={manejarEliminacion}
                            />
                        )
                    ))
                )}
            </div>
        </div>
    );
}

export default ListaResenas;
