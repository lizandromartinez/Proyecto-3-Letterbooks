import React, { useContext } from 'react';
import { useLocation, Navigate, Link } from 'react-router-dom'; 
import { ContextoSesion } from '../contexto/Sesion';

import AccionRecomendada from '../componentes/comunes/AccionRecomendada';
import Navbar from '../componentes/navegacion/navbar/Navbar';
import Footer from '../componentes/navegacion/footer/Footer';

/**
 * DetalleLibro - Componente de vista detallada de un libro en Letterbooks.
 * Captura, procesa y refleja dinámicamente los metadatos completos del libro 
 * transferidos desde el catálogo (Biblioteca) a través del estado de navegación.
 * Cuenta con medidas de seguridad para accesos huérfanos y rutas protegidas.
 */
const DetalleLibro = () => {
    // Contexto global para validar la persistencia de la sesión del usuario.
    const { token } = useContext(ContextoSesion);
    const location = useLocation();
    
    // Extracción segura del objeto de metadatos del libro enviado por el emisor.
    const libro = location.state?.libroData;

    // Guarda de seguridad: Redirección forzada al login si el usuario no está autenticado.
    if (!token) return <Navigate to="/login" replace />;
    
    // Control de excepciones: Renderizado de contingencia si se accede directamente a la URL sin datos en el estado.
    if (!libro) {
        return (
            <div className="p-20 text-center font-inter text-gray-500 bg-crema-fondo min-h-screen dark:bg-dark-fondo">
                <p className="mb-4">No se seleccionó ningún libro de forma válida.</p>
                <Link to="/biblioteca" className="text-[#d4a373] underline font-bold">
                    Volver a la Biblioteca
                </Link>
            </div>
        );
    }

    return (
        <div className="bg-crema-fondo min-h-screen dark:bg-dark-fondo transition-colors duration-500 flex flex-col font-inter text-navy-letter dark:text-gray-100">
            <Navbar estaAutenticado={true} />

            {/* --- CONTENEDOR ESTRUCTURAL EN GRID MODO FIGMA --- */}
            <main className="flex-grow py-12 px-4 sm:px-10 max-w-7xl mx-auto w-full grid grid-cols-1 lg:grid-cols-3 gap-8 items-start">
                
                {/* --- SECCIÓN PRINCIPAL: FICHA TÉCNICA DEL LIBRO (Ocupa 2/3 del ancho) --- */}
                <div className="lg:col-span-2 bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-2xl p-6 sm:p-8 flex flex-col sm:flex-row gap-8 shadow-xs text-left">
                    
                    {/* Contenedor adaptativo de la Portada */}
                    <div className="w-full sm:w-56 shrink-0 aspect-[2/3] rounded-xl overflow-hidden shadow-md bg-gray-100 dark:bg-white/5">
                        <img src={libro.imagen} alt={libro.titulo} className="w-full h-full object-cover" />
                    </div>

                    {/* Bloque descriptivo e informativo */}
                    <div className="flex flex-col flex-grow">
                        <h1 className="font-inter text-2xl font-bold text-gray-900 dark:text-white leading-tight">
                            {libro.titulo}
                        </h1>
                        <p className="text-gray-400 text-sm font-medium mt-1">
                            {libro.autor || libro.nombreAutor}
                        </p>

                        {/* Ratings y conteo global de interacción */}
                        <div className="flex items-center gap-1.5 mt-3 text-sm">
                            <div className="text-[#d4a373] flex gap-0.5 text-base">
                                <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
                            </div>
                            <span className="font-bold text-gray-800 dark:text-gray-200 ml-1">
                                {libro.calificacion || libro.promedioCalificacion}
                            </span>
                            <span className="text-gray-400 text-xs font-medium">
                                · {libro.totalResenas || 0} reseñas
                            </span>
                        </div>

                        {/* Etiqueta de clasificación por género */}
                        <div className="mt-4 self-start">
                            <span className="bg-orange-50 dark:bg-amber-900/20 text-[#d4a373] text-xs font-bold px-3 py-1 rounded-md border border-amber-900/5 dark:border-amber-700/20">
                                {libro.nombreGenero || 'Literatura'}
                            </span>
                        </div>

                        {/* Matriz secundaria de especificaciones técnicas */}
                        <div className="grid grid-cols-2 gap-3 mt-6 text-xs font-medium">
                            <div className="bg-gray-50 dark:bg-white/5 border border-gray-100 dark:border-white/5 rounded-xl p-3 flex flex-col gap-0.5">
                                <span className="text-gray-400">Año:</span>
                                <span className="text-gray-800 dark:text-gray-200 font-bold">{libro.ano || 'N/A'}</span>
                            </div>
                            <div className="bg-gray-50 dark:bg-white/5 border border-gray-100 dark:border-white/5 rounded-xl p-3 flex flex-col gap-0.5">
                                <span className="text-gray-400">Páginas:</span>
                                <span className="text-gray-800 dark:text-gray-200 font-bold">{libro.paginas || 'N/A'}</span>
                            </div>
                            <div className="bg-gray-50 dark:bg-white/5 border border-gray-100 dark:border-white/5 rounded-xl p-3 flex flex-col gap-0.5">
                                <span className="text-gray-400">Editorial:</span>
                                <span className="text-gray-800 dark:text-gray-200 font-bold line-clamp-1">{libro.nombreEditorial || 'N/A'}</span>
                            </div>
                            <div className="bg-gray-50 dark:bg-white/5 border border-gray-100 dark:border-white/5 rounded-xl p-3 flex flex-col gap-0.5">
                                <span className="text-gray-400">ISBN:</span>
                                <span className="text-gray-800 dark:text-gray-200 font-bold">{libro.isbn || 'N/A'}</span>
                            </div>
                        </div>

                        {/* Sinopsis del volumen */}
                        <div className="mt-6 border-t border-gray-100 dark:border-white/5 pt-4">
                            <h3 className="font-bold text-sm text-gray-900 dark:text-white mb-2">Sinopsis</h3>
                            <p className="text-gray-500 dark:text-gray-300 text-xs leading-relaxed font-normal">
                                {libro.sinopsis || 'Sin sinopsis disponible en el catálogo.'}
                            </p>
                        </div>

                        {/* Acceso directo al módulo de opiniones */}
                        <div className="mt-8">
                            <AccionRecomendada href="/" variante="primario">
                                Escribir Reseña
                            </AccionRecomendada>
                        </div>
                    </div>
                </div>

                {/* --- SECCIÓN LATERAL: CARD DE INTERACCIÓN PROPIA (Ocupa 1/3 del ancho) --- */}
                <div className="w-full bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-2xl p-6 shadow-xs text-center flex flex-col items-center justify-center min-h-[180px]">
                    <h3 className="text-xs font-bold text-gray-400 uppercase tracking-wider mb-4 self-start">
                        Tu calificación
                    </h3>
                    <div className="w-16 h-16 rounded-full bg-orange-50/50 dark:bg-white/5 border border-amber-900/5 dark:border-white/5 flex items-center justify-center text-gray-400 text-xl font-bold mb-3">
                        -
                    </div>
                    <p className="text-gray-400 text-xs font-medium">
                        no has calificado este libro
                    </p>
                </div>

            </main>

            <Footer estaAutenticado={true} />
        </div>
    );
};

export default DetalleLibro;