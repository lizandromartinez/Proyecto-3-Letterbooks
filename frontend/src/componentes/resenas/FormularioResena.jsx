import React, { useState, useContext, useEffect } from 'react';
import { ContextoSesion } from '../../contexto/Sesion';
import { crearResena, editarResena } from '../../api/Resenas';
import './FormularioResena.css';

/**
 * Componente de formulario para las reseñas.
 * @param {Object} props propiedades: idLibro, reseña previa (opcional) y callback de éxito.
 */
function FormularioResena({ idLibro, resenaAEditar, alCompletar, alCancelar }) {
    const { token } = useContext(ContextoSesion);
    const [calificacion, setCalificacion] = useState(5);
    const [texto, setTexto] = useState('');
    const [error, setError] = useState(null);

    useEffect(() => {
        if (resenaAEditar) {
            setCalificacion(resenaAEditar.calificacionLibro);
            setTexto(resenaAEditar.textoResena);
        }
    }, [resenaAEditar]);

    const manejarEnvio = async (evento) => {
        evento.preventDefault();
        setError(null);

        const datosFormulario = {
            idLibro: idLibro,
            calificacionLibro: calificacion,
            textoResena: texto
        };

        try {
            if (resenaAEditar) {
                await editarResena(resenaAEditar.idResena, datosFormulario, token);
            } else {
                await crearResena(datosFormulario, token);
            }
            alCompletar(); // Actualizamos sin recargar
        } catch (err) {
            setError(err.response?.data || "Ocurrió un error al guardar la reseña.");
        }
    };

    return (
        <div className="contenedor-formulario-resena">
            <h3>{resenaAEditar ? 'Editar Reseña' : 'Escribir una Reseña'}</h3>
            {error && <p className="mensaje-error">{error}</p>}
            
            <form onSubmit={manejarEnvio}>
                <div className="grupo-entrada">
                    <label>Calificación (1 a 5 estrellas):</label>
                    <input 
                        type="number" 
                        min="1" 
                        max="5" 
                        value={calificacion} 
                        onChange={(e) => setCalificacion(Number(e.target.value))}
                        className="entrada-pildora"
                        required
                    />
                </div>
                
                <div className="grupo-entrada">
                    <label>Tu opinión:</label>
                    <textarea 
                        value={texto} 
                        onChange={(e) => setTexto(e.target.value)}
                        className="entrada-pildora area-texto"
                        placeholder="Comparte tus pensamientos sobre el libro..."
                        required
                    />
                </div>

                {/* Mostrar el mensaje de error si existe */}
                {error && (
                    <div style={{ color: '#d32f2f', marginBottom: '12px', fontSize: '0.9rem', fontWeight: 'bold' }}>
                        {error}
                    </div>
                )}

                <div className="acciones-formulario">
                    {alCancelar && (
                        <button type="button" className="boton-pildora secundario" onClick={alCancelar}>
                            Cancelar
                        </button>
                    )}
                    <button type="submit" className="boton-pildora primario">
                        {resenaAEditar ? 'Actualizar' : 'Publicar'}
                    </button>
                </div>
            </form>
        </div>
    );
}

export default FormularioResena;
