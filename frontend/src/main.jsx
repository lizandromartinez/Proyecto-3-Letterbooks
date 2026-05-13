/**
 * ARCHIVO: main.jsx 
 * Punto de entrada de la aplicación React.
 * Este archivo se encarga de montar la aplicación en el DOM del navegador.
 */
import React from 'react'
import ReactDOM from 'react-dom/client'
import PaginaAterrizaje from './paginas/PaginaAterrizaje';

/**
 * ESTILOS GLOBALES
 * Importación de Tailwind CSS y configuraciones de fuentes/colores base
 * que afectarán a toda la aplicación (incluyendo el modo oscuro).
 */
import './estilos/global.css';

/**
 * RENDERIZADO INICIAL
 * document.getElementById('root') apunta al <div> vacío en index.html.
 */
ReactDOM.createRoot(document.getElementById('root')).render(
  /**
   * React.StrictMode:
   * Herramienta de desarrollo que ayuda a identificar problemas potenciales 
   * en la aplicación durante el renderizado.
   */
  <React.StrictMode>
    {/* Componente Raíz que orquesta la Landing Page */}
    <PaginaAterrizaje />
  </React.StrictMode>
)