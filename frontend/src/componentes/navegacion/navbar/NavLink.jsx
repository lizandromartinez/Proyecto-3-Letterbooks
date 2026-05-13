import React from 'react';

/**
 * NavLink - Componente de enlace de navegación optimizado.
 * Se encarga de renderizar los enlaces del menú con estilos diferenciados
 * entre la vista de escritorio (compacta y sutil) y la vista móvil (grande y táctil).
 * * @param {string} href - Dirección URL a la que apunta el enlace.
 * @param {React.ReactNode} children - El texto o elementos que irán dentro del enlace.
 * @param {boolean} esMovil - Si es true, aplica estilos de menú desplegable centrado.
 * @param {function} onClick - Función para manejar el evento clic (ej. cerrar el menú al navegar).
 */
const NavLink = ({ href, children, esMovil = false, onClick }) => {
  
  /**
   * estilosBase: 
   * Define la tipografía Inter y la transición de color.
   * - En móvil: usa mayúsculas y espaciado ancho para mejor lectura.
   * - En escritorio (md): vuelve a minúsculas y espaciado normal para un look más limpio.
   */
  const estilosBase = "transition-all duration-300 cursor-pointer font-inter uppercase tracking-wider md:normal-case md:tracking-normal";
  
  /**
   * estilosEscritorio:
   * Colores Navy en modo claro y Gris claro en modo oscuro.
   * Cambia a beige (gold-books-2) al pasar el mouse.
   */
  const estilosEscritorio = "text-navy-letter dark:text-gray-400 hover:text-gold-books-2 dark:hover:text-white text-[15px] font-medium";
  
  /**
   * estilosMovil:
   * Texto más grande (xl) y seminegrilla.
   * Ocupa todo el ancho disponible y centra el texto para el menú hamburguesa.
   */
  const estilosMovil = "text-navy-letter dark:text-white text-xl font-semibold w-full text-center py-4 hover:text-gold-books-2";

  return (
    <a 
      href={href} 
      onClick={onClick} 
      className={`
        ${estilosBase} 
        ${esMovil ? estilosMovil : estilosEscritorio}
      `}
    >
      {children}
    </a>
  );
};

export default NavLink;