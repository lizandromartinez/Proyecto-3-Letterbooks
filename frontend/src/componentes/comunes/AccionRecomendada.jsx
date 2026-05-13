import React from 'react';

/**
 * AccionRecomendada - Componente de botón/enlace de alta jerarquía.
 * Este componente unifica la estética de las acciones principales del sitio.
 * Se comporta como una etiqueta <a> para permitir navegación, pero mantiene
 * la apariencia de un botón con estados de hover animados.
 * @param {string} href - Ruta de destino del enlace (por defecto "/").
 * @param {React.ReactNode} children - Contenido a mostrar dentro del botón (texto, iconos, etc.).
 * @param {'primario' | 'outline'} variante - Estilo visual del botón.
 * @param {function} onClick - Función opcional para manejar eventos de clic (ej. cerrar menú móvil).
 * @param {boolean} esMovil - Si es verdadero, el botón ocupa casi todo el ancho y aumenta su tamaño de fuente.
 */
const AccionRecomendada = ({ 
  href = "/", 
  children, 
  variante = 'primario', 
  onClick, 
  esMovil = false 
}) => {
  
  // Definición de estilos dinámicos basados en la variante y el modo (Light/Dark)
  const estilos = {
    /**
     * Variante Primaria: Fondo sólido beige en Light Mode y Navy en Dark Mode.
     * Incluye cambio de brillo sutil en hover.
     */
    primario: `
      bg-gold-books-2 dark:bg-navy-button
      text-white 
      hover:bg-[#b3936a] dark:hover:bg-[#1e294b]
      shadow-md
    `,
    /**
     * Variante Outline: Bordes definidos sin fondo inicial.
     * Al hacer hover, se rellena con el color correspondiente.
     */
    outline: `
      border-2 border-gold-books-2 dark:border-navy-button
      text-gold-books-2 dark:text-gray-200 
      hover:bg-gold-books-2 dark:hover:bg-navy-button 
      hover:text-white
    `
  };

  /**
   * Clases de tamaño condicionales:
   * - Móvil: Ancho del 80%.
   * - Escritorio: Tamaño compacto.
   */
  const tamaño = esMovil 
    ? "w-4/5 py-3 text-lg flex justify-center" 
    : "px-6 py-2 text-sm inline-block";

  return (
    <a 
      href={href}
      onClick={onClick}
      className={`
        ${estilos[variante]} 
        ${tamaño} 
        rounded-md 
        transition-all duration-500 ease-in-out
        transform hover:scale-110 hover:shadow-xl 
        cursor-pointer 
        font-inter font-bold text-center
      `}
    >
      {children}
    </a>
  );
};

export default AccionRecomendada;