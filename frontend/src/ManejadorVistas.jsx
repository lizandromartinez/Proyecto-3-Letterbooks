import React, { useContext } from 'react';
import { ContextoSesion } from './contexto/Sesion';
import InicioDeSesion from './componentes/InicioDeSesion';

/**
 * Componente principal que actúa como controlador de vistas.
 * Decide qué pantalla mostrar al usuario según su estado de autenticación.
 */
function ManejadorVistas() {
  const { token, cerrarSesion } = useContext(ContextoSesion);

  return (
    <div className="contenedor-principal">
      {!token ? (
        <InicioDeSesion />
      ) : (
        <div className="dashboard">
          <h1>Bienvenido a LetterBooks</h1>
          <p>Tu sesión está activa y el sistema está listo.</p>
          <button onClick={cerrarSesion}>Cerrar Sesión</button>
        </div>
      )}
    </div>
  );
}

export default ManejadorVistas;
