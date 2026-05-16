import React from 'react';

/**
 * COMPONENTE: Cita
 * Este componente es un "átomo" de la interfaz, diseñado para resaltar fragmentos
 * de texto (citas literarias) extraídos de los libros. 
 * * @param {string} texto - El contenido de la cita que se mostrará entre comillas.
 * @param {string|number} pagina - El número de página opcional de donde proviene la cita.
 */
const Cita = ({ texto, pagina }) => {
    // RENDERIZADO CONDICIONAL: 
    // Si no hay texto, el componente no ocupa espacio en el DOM.
    if (!texto) 
        return null; 

    return (
        <div className="
            /* Diseño de Bloque */
            bg-gray-50 dark:bg-white/5 
            border-l-4 border-gold-button 
            p-5 rounded-r-2xl my-4 
            
            /* Animación y Hover */
            transition-all duration-300
            hover:bg-gray-100 dark:hover:bg-white/10
        ">
            {/* CUERPO DE LA CITA */}
            <p className="text-gray-700 dark:text-gray-300 leading-relaxed font-serif italic">
                "{texto}"
            </p>

            {/* REFERENCIA DE PÁGINA (Opcional) */}
            {pagina && (
                <span className="
                    text-gray-600 dark:text-gray-300 
                    text-xs mt-3 block 
                    font-medium uppercase tracking-wider
                ">
                    — Página {pagina}
                </span>
            )}
        </div>
    );
};

export default Cita;