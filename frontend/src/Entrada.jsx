import React from 'react';
import ReactDOM from 'react-dom/client';
import ManejadorVistas from './ManejadorVistas';
import { ProveedorSesion } from './contexto/Sesion';
import './estilos/Globales.css';

/**
 * Punto de entrada de la aplicación React.
 * Configura el Proveedor de Sesión global y renderiza el componente principal.
 */
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ProveedorSesion>
      <ManejadorVistas />
    </ProveedorSesion>
  </React.StrictMode>
);
