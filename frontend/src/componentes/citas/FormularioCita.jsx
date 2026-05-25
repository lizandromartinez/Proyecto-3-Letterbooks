import React, { useState } from 'react';
import './FormularioCita.css';

/**
 * Componente de formulario individual para redactar una cita.
 * Maneja su propio estado y emite la cita a Formulario Reseña al completarse.
 * * @param {Object} props propiedades: alAgregar (callback) y deshabilitado (booleano).
 */
function FormularioCita({ alAgregar, deshabilitado }) {
    const [texto, setTexto] = useState('');
    const [pagina, setPagina] = useState('');

    const manejarAgregado = () => {
        if (!texto.trim()) return;

        // Enviamos la cita a Formulario Reseña
        alAgregar({ 
            texto: texto.trim(), 
            pagina: pagina ? parseInt(pagina, 10) : null 
        });

        // Limpiamos los campos locales
        setTexto('');
        setPagina('');
    };

    return (
        <div className="contenedor-agregar-cita">
            <textarea 
                value={texto}
                onChange={(e) => setTexto(e.target.value)}
                className="entrada-pildora area-texto-cita"
                placeholder="Escribe una cita que te haya gustado..."
                disabled={deshabilitado}
            />
            <div className="controles-cita">
                <input 
                    type="number" 
                    value={pagina}
                    onChange={(e) => setPagina(e.target.value)}
                    className="entrada-pildora entrada-pagina"
                    placeholder="Página (opcional)"
                    disabled={deshabilitado}
                    min="1"
                />
                <button 
                    type="button" 
                    className="boton-pildora secundario boton-agregar-cita"
                    onClick={manejarAgregado}
                    disabled={deshabilitado || !texto.trim()}
                >
                    + Agregar cita
                </button>
            </div>
        </div>
    );
}

export default FormularioCita;
