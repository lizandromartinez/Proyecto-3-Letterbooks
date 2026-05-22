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
function ListaResenas({ idLibro, alCambiarResenas }) {
    const { token } = useContext(ContextoSesion);
    const [resenas, setResenas] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [resenaEditando, setResenaEditando] = useState(null);

    const usuarioActual = obtenerUsuarioDelToken(token);

    const cargarResenas = async () => {
        try {
            setCargando(true);
            const datos = await obtenerResenas(idLibro);
            setResenas(datos);
            if (alCambiarResenas) {
                alCambiarResenas();
            }
        } catch (error) {
            console.error("Error al cargar reseñas:", error);
        } finally {
            setCargando(false);
        }
    };

    useEffect(() => {
        cargarResenas();
    }, [idLibro]);

    const manejarEliminacion = async (idResena) => {
        if (window.confirm("¿Estás seguro de eliminar esta reseña?")) {
            try {
                await eliminarResena(idResena, token);
                cargarResenas();
            } catch (error) {
                alert("No se pudo eliminar la reseña.");
            }
        }
    };

    if (cargando) return <p>Cargando opiniones...</p>;

    return (
        <div className="contenedor-lista-resenas">
            <h2 className="titulo-seccion">Opiniones de Lectores</h2>
            
            {/* Formulario principal para nueva reseña */}
            {token && !resenaEditando && (
                <FormularioResena 
                    idLibro={idLibro} 
                    alCompletar={cargarResenas} 
                />
            )}

            <div className="listado-tarjetas">
                {resenas.length === 0 ? (
                    <p className="mensaje-vacio">Sé el primero en opinar sobre este libro.</p>
                ) : (
                    resenas.map((resena) => (
                        <div key={resena.idResena} className="tarjeta-resena">
                            <div className="cabecera-tarjeta">
                                <span className="autor-reseña">@{resena.usuario.nombreUsuario}</span>
                                <span className="calificacion-estrellas">
                                    {resena.calificacionLibro} / 5 Estrellas
                                </span>
                            </div>
                            <p className="texto-reseña">{resena.textoResena}</p>
                            <span className="fecha-reseña">{resena.fechaPublicacion}</span>

                            {/* Controles de autoría */}
                            {usuarioActual === resena.usuario.nombreUsuario && (
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
                                        cargarResenas();
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
