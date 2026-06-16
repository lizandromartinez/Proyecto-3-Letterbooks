import React, { useState, useContext } from 'react';
import Logo from '../../comunes/Logo';
import NavLink from './NavLink';
import AccionRecomendada from '../../comunes/AccionRecomendada';
import EnlaceAvatarPerfil from './EnlaceAvatarPerfil';
import { useNavigate } from 'react-router-dom';
import { ContextoSesion } from '../../../contexto/Sesion';

/**
 * Navbar - Componente de navegación principal y responsivo.
 * Gestiona el estado del menú colapsable para móviles y adapta las opciones
 * de navegación dependiendo de si el usuario está autenticado o no.
 * @param {boolean} estaAutenticado - Prop que define qué conjunto de enlaces mostrar.
 * @param {string|null} avatarUrl - Ruta del avatar para actualización inmediata en la página de perfil.
 */
const Navbar = ({ estaAutenticado = false, avatarUrl }) => {
    // Estado local para controlar la apertura/cierre del menú en dispositivos móviles.
    const [isOpen, setIsOpen] = useState(false);

    const [mostrarModalLogout, setMostrarModalLogout] = useState(false);
    
    const navigate = useNavigate();

    const { cerrarSesion } = useContext(ContextoSesion);

    /** Alterna el estado del menú móvil */
    const alternaMenu = () => setIsOpen(!isOpen);

    /** Garantiza el cierre del menú al hacer clic en un enlace */
    const cerrarMenu = () => setIsOpen(false);

    const confirmarLogout = () => {
        cerrarSesion();
        navigate('/');
    };
    
    return (
	<>
	
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
                            <NavLink href="/dashboard">Inicio</NavLink>
                            <NavLink href="/dashboard">Explorar</NavLink>
                            <NavLink href="/biblioteca">Biblioteca</NavLink>
                            <NavLink href="/registrarLibro">Nuevo Libro</NavLink>
                            <EnlaceAvatarPerfil avatarUrl={avatarUrl} />
			    {/* Botón cerrar sesión */}
                            <button
                                onClick={() => setMostrarModalLogout(true)}
                                className="flex items-center gap-2 px-4 py-2 rounded-md border border-red-400 text-red-500 font-inter text-sm font-medium hover:bg-red-50 dark:hover:bg-red-950/30 transition-all duration-200 cursor-pointer"
                            >
                                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                                </svg>
                            </button>
                        </>
                    )}
                </div>
            </div>

            {/* --- MENÚ MÓVIL DESPLEGABLE --- */}
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
                            <NavLink href="/dashboard" esMovil onClick={cerrarMenu}>Inicio</NavLink>
                            <NavLink href="/dashboard" esMovil onClick={cerrarMenu}>Explorar</NavLink>
                            <NavLink href="/biblioteca" esMovil onClick={cerrarMenu}>Biblioteca</NavLink>
                            <NavLink href="/registrarLibro" esMovil onClick={cerrarMenu}>Nuevo Libro</NavLink>
                            <EnlaceAvatarPerfil avatarUrl={avatarUrl} onClick={cerrarMenu} />
			    <button
                                onClick={() => { cerrarMenu(); setMostrarModalLogout(true); }}
                                className="flex items-center gap-2 px-5 py-2 rounded-md border border-red-400 text-red-500 font-inter text-sm font-medium hover:bg-red-50 dark:hover:bg-red-950/30 transition-all duration-200 cursor-pointer"
                            >
                                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                                </svg>
					  Cerrar sesión
                            </button>
                        </>
                    )}
                </div>
            </div>
        </nav>
	    
	    {/* Modal de confirmación de logout */}
            {mostrarModalLogout && (
                <div className="fixed inset-0 z-50 flex items-center justify-center">
                    {/* Fondo oscuro */}
                    <div
                        className="absolute inset-0 bg-black/40 backdrop-blur-sm"
                        onClick={() => setMostrarModalLogout(false)}
                    />
                    {/* Tarjeta del modal */}
                    <div className="relative bg-white dark:bg-dark-borde rounded-2xl shadow-xl border border-gray-100 dark:border-white/5 p-8 max-w-sm w-full mx-4 flex flex-col items-center gap-5">
                        <div className="w-14 h-14 rounded-full bg-red-50 dark:bg-red-950/30 flex items-center justify-center">
                            <svg className="w-7 h-7 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                            </svg>
                        </div>
                        <div className="text-center">
                            <h3 className="font-cormorant text-xl font-bold text-navy-letter dark:text-gray-100 mb-1">
															  ¿Cerrar sesión?
                            </h3>
                            <p className="font-inter text-sm text-gray-500 dark:text-gray-400">
												   Tendrás que volver a iniciar sesión para acceder a tu cuenta.
                            </p>
                        </div>
                        <div className="flex gap-3 w-full">
                            <button
                                onClick={() => setMostrarModalLogout(false)}
                                className="flex-1 px-4 py-2.5 rounded-lg border border-gray-300 dark:border-gray-600 text-navy-letter dark:text-gray-200 font-inter text-sm font-medium hover:bg-gray-100 dark:hover:bg-dark-fondo transition-all duration-200 cursor-pointer"
                            >
                                 Cancelar
                            </button>
                            <button
                                onClick={confirmarLogout}
                                className="flex-1 px-4 py-2.5 rounded-lg bg-red-500 hover:bg-red-600 text-white font-inter text-sm font-bold transition-all duration-200 cursor-pointer"
                            >
                                 Cerrar sesión
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
};

export default Navbar;
