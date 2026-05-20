import React from 'react';
import Logo from '../../comunes/Logo';
import NavLink from '../navbar/NavLink';
import AccionRecomendada from '../../comunes/AccionRecomendada';

/**
 * Footer - Pie de página unificado.
 * @param {boolean} estaAutenticado - Determina el texto y destino de las acciones.
 */
const Footer = ({ estaAutenticado = false }) => {
    
    const rutaBase = "/";

    return (
        <footer className="bg-dark-borde text-white py-16 px-10 grid grid-cols-1 content-center">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-12 pb-12 border-b border-gray-700/50">
            
                {/* COLUMNA 1 : Identidad*/}
                <div className="flex flex-col gap-6">
                    <Logo className="[&_span:first-child]:text-gray-100 [&_span:last-child]:text-gold-button" />   
                    <p className="text-gray-400 font-inter">
                        La plataforma para amantes de los libros. Descubre, comparte y conecta con otros lectores.
                    </p>
                </div>

                {/* COLUMNA 2: Navegación de Comunidad */}
                <div className="flex flex-col gap-4">
                    <h4 className="font-cormorant text-xl font-bold tracking-widest text-gray-100 dark:text-crema-fondo uppercase">
                        Comunidad
                    </h4>
                    <nav className="flex flex-col gap-2">
                        <NavLink href={estaAutenticado ? "/" : rutaBase}>Explorar</NavLink>
                        <NavLink href={estaAutenticado ? "/" : rutaBase}>Reseñas</NavLink>
                        <NavLink href={estaAutenticado ? "/" : rutaBase}>Usuarios</NavLink>
                        
                    </nav>
                </div>

                {/* COLUMNA 2: Navegación de Acerca De*/}
                <div className="flex flex-col gap-4">
                    <h4 className="font-cormorant text-xl font-bold tracking-widest text-gray-100 dark:text-crema-fondo uppercase">
                        Acerca De
                    </h4>
                    <nav className="flex flex-col gap-2">
                        <NavLink href={estaAutenticado ? "/" : rutaBase}>Nosotros</NavLink>
                        <NavLink href={estaAutenticado ? "/" : rutaBase}>Blog</NavLink>
                        <NavLink href={estaAutenticado ? "/" : rutaBase}>Contacto</NavLink>                        
                    </nav>
                </div>
            </div>
            <div className="pt-10 flex flex-col items-center justify-center gap-2">
                    <p className="text-gray-400 font-inter text-sm text-center">
                        © 2026 Letter Books. Hecho por y para los amantes de los libros.
                    </p>
            </div>
        </footer>
    );
};

export default Footer;