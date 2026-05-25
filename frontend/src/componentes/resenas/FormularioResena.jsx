import React, { useState, useEffect, useContext } from 'react';
import { crearResena, editarResena } from '../../api/Resenas';
import { ContextoSesion } from '../../contexto/Sesion';
import FormularioCita from './FormularioCita';
import './FormularioResena.css';

/**
 * Componente principal para redactar, editar y gestionar el estado
 * dinámico de una reseña junto a sus citas asociadas.
 */
function FormularioResena({ idLibro, resenaAEditar, alCompletar, alCancelar }) {
    const { token } = useContext(ContextoSesion);

    // Estados principales del formulario
    const [calificacion, setCalificacion] = useState(0);
    const [textoResena, setTextoResena] = useState('');
    const [citas, setCitas] = useState([]); // Colección dinámica de citas (máx 3)
    const [enviando, setEnviando] = useState(false);
    const [error, setError] = useState(null);

    // Estado para controlar el hover visual de las estrellas
    const [estrellaHover, setEstrellaHover] = useState(0);

    // Efecto para cargar datos preexistentes si estamos en modo edición
    useEffect(() => {
        if (resenaAEditar) {
            setCalificacion(resenaAEditar.calificacionLibro);
            setTextoResena(resenaAEditar.textoResena);
            setCitas(resenaAEditar.citas || []);
        }
    }, [resenaAEditar]);

    /**
     * Agrega una cita validada al estado dinámico de la reseña.
     * @param {Object} nuevaCita texto y página opcional.
     */
    const manejarAgregarCita = (nuevaCita) => {
        if (citas.length >= 3) return;
        setCitas([...citas, nuevaCita]);
    };

    /**
     * Remueve una cita del arreglo antes de enviar el formulario.
     * @param {number} indice el índice de la cita a remover.
     */
    const manejarRemoverCita = (indice) => {
        setCitas(citas.filter((_, i) => i !== indice));
    };

    /**
     * Envía la estructura unificada del DTO hacia el backend.
     */
    const manejarEnvio = async (e) => {
        e.preventDefault();
        if (calificacion === 0) {
            setError("Por favor, selecciona una calificación en estrellas.");
            return;
        }
        if (!textoResena.trim()) {
            setError("El cuerpo de la reseña no puede estar vacío.");
            return;
        }

        try {
            setEnviando(true);
            setError(null);

            const datosPayload = {
                idLibro,
                calificacionLibro: calificacion,
                textoResena: textoResena.trim(),
                citas: citas 
            };

            if (resenaAEditar) {
                await editarResena(resenaAEditar.idResena, datosPayload, token);
            } else {
                await crearResena(datosPayload, token);
            }

            setCalificacion(0);
            setTextoResena('');
            setCitas([]);
            if (alCompletar) alCompletar();
        } catch (err) {
            console.error(err);
            setError("Ocurrió un error al procesar tu opinión. Inténtalo de nuevo.");
        } finally {
            setEnviando(false);
        }
    };

    return (
        <form onSubmit={manejarEnvio} className="tarjeta-formulario-resena">
            <h3 className="subtitulo-formulario">
                {resenaAEditar ? 'Modificar tu reseña' : 'Tu reseña'}
            </h3>

            {/* Sistema Visual de Estrellas Interactivas */}
            <div className="seccion-campo-estrellas">
                <label className="etiqueta-formulario">Calificación</label>
                <div className="contenedor-estrellas-interactivas">
                    {[1, 2, 3, 4, 5].map((num) => (
                        <span
                            key={num}
                            className={`icono-estrella ${num <= (estrellaHover || calificacion) ? 'activa' : ''}`}
                            onClick={() => setCalificacion(num)}
                            onMouseEnter={() => setEstrellaHover(num)}
                            onMouseLeave={() => setEstrellaHover(0)}
                        >
                            ★
                        </span>
                    ))}
                </div>
            </div>

            {/* Campo Cuerpo de Opinión */}
            <div className="seccion-campo-texto">
                <label className="etiqueta-formulario">Tu opinión</label>
                <textarea
                    value={textoResena}
                    onChange={(e) => setTextoResena(e.target.value)}
                    className="entrada-pildora area-texto-opinion"
                    placeholder="¿Qué te pareció este libro? Comparte tus pensamientos..."
                    maxLength={2000}
                />
            </div>

            {/* Sección de Citas Favoritas Integrada */}
            <div className="seccion-bloque-citas">
                <label className="etiqueta-formulario">Citas favoritas (opcional)</label>
                
                {/* Lista de citas añadidas temporalmente */}
                {citas.length > 0 && (
                    <div className="contenedor-previsualizacion-citas">
                        {citas.map((cita, index) => (
                            <div key={index} className="pildora-cita-previa">
                                <p className="resumen-texto-pildora">
                                    "{cita.texto}" {cita.pagina && `— Pág. ${cita.pagina}`}
                                </p>
                                <button
                                    type="button"
                                    className="boton-remover-pildora"
                                    onClick={() => manejarRemoverCita(index)}
                                    title="Remover cita"
                                >
                                    ×
                                </button>
                            </div>
                        ))}
                    </div>
                )}

                {/* Subformulario dinámico inyectado */}
                <FormularioCita 
                    alAgregar={manejarAgregarCita} 
                    deshabilitado={citas.length >= 3} 
                />
                {citas.length >= 3 && (
                    <p className="mensaje-limite-citas">Has alcanzado el límite máximo de 3 citas por reseña.</p>
                )}
            </div>

            {error && <p className="mensaje-error-formulario">{error}</p>}

            {/* Botonera de Acciones Finales */}
            <div className="acciones-formulario-pie">
                <button
                    type="submit"
                    className="boton-pildora primario"
                    disabled={enviando}
                >
                    {resenaAEditar ? 'Guardar cambios' : 'Publicar reseña'}
                </button>
                {alCancelar && (
                    <button
                        type="button"
                        className="boton-pildora cancelar"
                        onClick={alCancelar}
                    >
                        Cancelar
                    </button>
                )}
            </div>
        </form>
    );
}

export default FormularioResena;
