import React, { useState } from 'react';
import Logo from '../../comunes/Logo';
import NavLink from './NavLink';
import AccionRecomendada from '../../comunes/AccionRecomendada';

/**
 * Navbar - Componente de navegación principal y responsivo.
 * Gestiona el estado del menú colapsable para móviles y adapta las opciones
 * de navegación dependiendo de si el usuario está autenticado o no.
 * * @param {boolean} estaAutenticado - Prop que define qué conjunto de enlaces mostrar.
 */
const Navbar = ({ estaAutenticado = false }) => {
    // Estado local para controlar la apertura/cierre del menú en dispositivos móviles.
    const [isOpen, setIsOpen] = useState(false);

    /** Alterna el estado del menú móvil */
    const alternaMenu = () => setIsOpen(!isOpen);
    
    /** Garantiza el cierre del menú al hacer clic en un enlace */
    const cerrarMenu = () => setIsOpen(false);

    return (
        <nav className="sticky top-0 z-50 bg-crema-fondo dark:bg-dark-fondo border-b border-gray-200 dark:border-dark-borde shadow-sm">
            
            {/* --- CONTENEDOR DE LA BARRA PRINCIPAL --- */}
            <div className="flex items-center justify-between px-10 py-4 max-w-7xl mx-auto">
                
                {/* Identidad visual de la marca */}
                <Logo />

                {/* BOTÓN HAMBURGUESA (Solo visible en pantallas pequeñas < md) */}
                <button 
                    onClick={alternaMenu} 
                    className="md:hidden text-navy-letter dark:text-white p-2 cursor-pointer z-50 focus:outline-none"
                    aria-label="Abrir menú de navegación"
                >
                    <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path 
                            strokeLinecap="round" 
                            strokeLinejoin="round" 
                            strokeWidth={2} 
                            /* Cambia dinámicamente entre el icono de hamburguesa y la X */
                            d={isOpen ? "M6 18L18 6M6 6l12 12" : "M4 6h16M4 12h16M4 18h16"}
                        />
                    </svg>
                </button>

                {/* MENÚ ESCRITORIO (Oculto en móvil, visible en md+) */}
                <div className="hidden md:flex items-center gap-8">
                    {!estaAutenticado ? (
                        /* VISTA: Invitado */
                        <>
                            <NavLink href="/login">Iniciar sesión</NavLink>
                            <AccionRecomendada href="/registro" variante="primario">
                                Registrarse
                            </AccionRecomendada>
                        </>
                    ) : (
                        /* VISTA: Usuario Logueado */
                        <>
                            <NavLink href="/dashboard">Feed</NavLink>
                            <NavLink href="/dashboard">Explorar</NavLink>
                            <NavLink href="/dashboard">Nuevo Libro</NavLink>
                            <AccionRecomendada href="/dashboard" variante="primario">
                                Mi Perfil
                            </AccionRecomendada>
                        </>
                    )}
                </div>
            </div>

            {/* --- MENÚ MÓVIL DESPLEGABLE --- 
                Utiliza transformaciones de CSS para una animación de deslizamiento superior.
            */}
            <div className={`
                ${isOpen ? "translate-y-0 opacity-100" : "-translate-y-full opacity-0 pointer-events-none"} 
                md:hidden absolute top-0 left-0 w-full bg-crema-fondo dark:bg-dark-fondo border-b border-gray-200 dark:border-dark-borde 
                pt-24 pb-10 transition-all duration-300 ease-in-out -z-10 shadow-xl
            `}>
                <div className="flex flex-col items-center gap-6 px-10">
                    {!estaAutenticado ? (
                        <>
                            <NavLink href="/login" esMovil onClick={cerrarMenu}>Iniciar sesión</NavLink>
                            <AccionRecomendada href="/registro" variante="outline" esMovil onClick={cerrarMenu}>
                                Registrarse
                            </AccionRecomendada>
                        </>
                    ) : (
                        <>
                            <NavLink href="/dashboard" esMovil onClick={cerrarMenu}>Feed</NavLink>
                            <NavLink href="/dashboard" esMovil onClick={cerrarMenu}>Explorar</NavLink>
                            <NavLink href="/dashboard" esMovil onClick={cerrarMenu}>Nuevo Libro</NavLink>
                            <AccionRecomendada href="/dashboard" variante="outline" esMovil onClick={cerrarMenu}>
                                Mi Perfil
                            </AccionRecomendada>
                        </>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default Navbar;