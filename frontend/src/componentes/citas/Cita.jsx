import React, { useState, useEffect, useContext } from 'react';
import { alternarLikeCita } from '../../api/Citas';
import { ContextoSesion } from '../../contexto/Sesion'; 
import './Cita.css';

/**
 * Componente que renderiza una cita individual dentro de una reseña.
 * Maneja visualmente el diseño editorial y la interacción de "Me gusta".
 * @param {Object} props contiene el objeto cita.
 */
function Cita({ cita }) {
    const { token } = useContext(ContextoSesion);
    
    // Estados locales para una actualización instantánea en la interfaz
    const [likesTotales, setLikesTotales] = useState(cita.likes || 0);
    const [dioLike, setDioLike] = useState(false);

    /**
     * Sincroniza el estado local de los likes si los datos cambian.
     * Evita que la UI se quede congelada al editar, eliminar o recargar la lista.
     */
    useEffect(() => {
        setLikesTotales(cita.likes || 0);
    }, [cita.likes]);

    /**
     * Gestiona la petición de 'Me gusta' comunicándose con el backend
     * y actualizando el estado.
     */
    const manejarLike = async () => {
        if (!token) {
            alert("Debes iniciar sesión para dar me gusta a una cita.");
            return;
        }

        try {
            const respuesta = await alternarLikeCita(cita.idCita, token);
            
            if (respuesta.likeActivo) {
                setLikesTotales(likesTotales + 1);
                setDioLike(true);
            } else {
                setLikesTotales(likesTotales - 1);
                setDioLike(false);
            }
        } catch (error) {
            console.error("Error al registrar el like:", error);
        }
    };

    return (
        <div className="contenedor-cita-publicada">
            {/* Cuerpo de la cita entrecomillado */}
            <p className="texto-cita-publicada">"{cita.texto}"</p>
            
            {/* Número de página opcional de donde se extrajo */}
            {cita.pagina && (
                <span className="pagina-cita-publicada">— Página {cita.pagina}</span>
            )}
            
            {/* Bloque interactivo del corazón de likes */}
            <div className="acciones-cita-publicada">
                <button 
                    className={`boton-like-cita ${dioLike ? 'activo' : ''}`}
                    onClick={manejarLike}
                    title="Me gusta"
                >
                    <span className="icono-corazon">{dioLike ? '♥' : '♡'}</span>
                    <span className="contador-likes">{likesTotales}</span>
                </button>
            </div>
        </div>
    );
}

export default Cita;
