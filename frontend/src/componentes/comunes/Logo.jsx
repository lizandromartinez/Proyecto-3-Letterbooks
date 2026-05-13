import React from 'react';
import logoLibro from '../../estilos/img/iconos/logo.png'; 

/**
 * Logo - Identidad visual principal de Letterbooks.
 * Este componente es reutilizable tanto en el Navbar como en el Footer.
 * Utiliza la tipografía 'Cormorant' para un acabado elegante y clásico.
 * * @param {string} className - Clases adicionales de Tailwind para ajustar margen o posición.
 */
const Logo = ({ className = "" }) => (
  <div className={`flex items-center font-cormorant select-none ${className}`}>
    
    {/* Texto 'LETTER' */}
    <span className="text-navy-letter dark:text-gray-100 font-bold text-3xl tracking-[0.25em]">
      LETTER
    </span>
    
    {/* Icono del Libro */}
    <img 
      src={logoLibro} 
      alt="Logo" 
      className="mx-4 h-8 w-auto dark:invert dark:brightness-200 transition-all duration-300" 
    />
    
    {/* Texto 'BOOKS' */}
    <span className="text-gold-books-2 font-medium text-3xl tracking-[0.25em]">
      BOOKS
    </span>
  </div>
);

export default Logo;