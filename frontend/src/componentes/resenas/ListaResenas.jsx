import React, { useState, useEffect, useContext } from 'react';
import { obtenerResenas, eliminarResena } from '../../api/Resenas';
import { ContextoSesion } from '../../contexto/Sesion';
import { obtenerUsuarioDelToken } from '../../utilidades/DecodificadorToken';
import FormularioResena from './FormularioResena';
import './ListaResenas.css';

/**
 * Componente que muestra la lista de reseñas de un libro.
 * @param {Object} props contiene el idLibro y opcionalmente alCambiarResenas.
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
            setErrorCarga("No se pudieron cargar las reseñas. Intenta recargar la página.");
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

            <div className="listado-tarjetas">
                {resenas.length === 0 && !errorCarga ? (
                    <p className="mensaje-vacio">Sé el primero en opinar sobre este libro.</p>
                ) : (
                    resenas.map((resena) => (
                        <div key={resena.idResena} className="tarjeta-resena">
                            <div className="cabecera-tarjeta">
                                <span className="autor-reseña">@{resena.usuario?.nombreUsuario ?? 'usuario'}</span>
                                <span className="calificacion-estrellas">
                                    {resena.calificacionLibro} / 5 Estrellas
                                </span>
                            </div>
                            <p className="texto-reseña">{resena.textoResena}</p>
                            <span className="fecha-reseña">{resena.fechaPublicacion}</span>

                            {/* Controles de autoría */}
                            {usuarioActual === resena.usuario?.nombreUsuario && (
                                <div className="acciones-autor">
                                    <button 
                                        className="boton-accion-pildora"
                                        onClick={() => setResenaEditando(resena)}
                                    >
                                        Editar
                                    </button>
                                    <button 
                                        className="boton-accion-pildora peligro"
                                        onClick={() => manejarEliminacion(resena.idResena)}
                                    >
                                        Eliminar
                                    </button>
                                </div>
                            )}

                            {/* Mostrar formulario de edición anidado si corresponde */}
                            {resenaEditando?.idResena === resena.idResena && (
                                <FormularioResena 
                                    idLibro={idLibro}
                                    resenaAEditar={resenaEditando}
                                    alCompletar={() => {
                                        setResenaEditando(null);
                                        cargarResenas(true);
                                    }}
                                    alCancelar={() => setResenaEditando(null)}
                                />
                            )}
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

export default ListaResenas;
