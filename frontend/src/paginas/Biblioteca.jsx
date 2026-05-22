import React, { useContext, useState, useEffect } from 'react';
import { Link, Navigate } from 'react-router-dom'; 
import { ContextoSesion } from '../contexto/Sesion';

import AccionRecomendada from '../componentes/comunes/AccionRecomendada';
import Navbar from '../componentes/navegacion/navbar/Navbar';
import Footer from '../componentes/navegacion/footer/Footer';

import cuadricula from '../estilos/img/iconos/cuadricula.png';
import lista from '../estilos/img/iconos/lista.png';
import { obtenerTodosLosLibros } from '../api/Libros';

/**
 * Biblioteca - Componente del catálogo general de libros de Letterbooks.
 * Proporciona controles interactivos para ordenar el inventario, alternar la 
 * visualización de la interfaz y redirigir el flujo hacia la vista detallada.
 * Protege la ruta verificando activamente el estado de autenticación.
 */
const Biblioteca = () => {
    // Contexto global para verificar si el usuario tiene una sesión activa (token).
    const { token } = useContext(ContextoSesion);
    
    // Estado local para determinar el criterio de ordenamiento activo (título, autor, calificación).
    const [criterioOrden, setCriterioOrden] = useState('titulo');
    
    // Estado local para alternar la cuadrícula (true) o el formato de lista (false).
    const [vistaGrid, setVistaGrid] = useState(true);

    // Estado local para almacenar la lista de libros del backend.
    const [libros, setLibros] = useState([]);

    // Fetch books from backend
    useEffect(() => {
        const cargarLibros = async () => {
            try {
                const data = await obtenerTodosLosLibros(token);
                setLibros(data);
            } catch (err) {
                console.error("Error al cargar libros:", err);
            }
        };
        if (token) {
            cargarLibros();
        }
    }, [token]);

    // Guarda de seguridad: Si no existe sesión válida, redirige de inmediato al login.
    if (!token) {
        return <Navigate to="/login" replace />;
    }

    // Helper to format image URLs
    const obtenerUrlImagen = (ruta) => {
        if (!ruta) return 'https://via.placeholder.com/300x450?text=Sin+Portada';
        if (ruta.startsWith('http')) return ruta;
        return `http://localhost:8080${ruta}`;
    };

    // Sort books based on selected criterion
    const librosOrdenados = [...libros].sort((a, b) => {
        if (criterioOrden === 'titulo') {
            return (a.titulo || '').localeCompare(b.titulo || '');
        } else if (criterioOrden === 'autor') {
            const nombreA = a.autor?.nombreAutor || a.nombreAutor || '';
            const nombreB = b.autor?.nombreAutor || b.nombreAutor || '';
            return nombreA.localeCompare(nombreB);
        } else if (criterioOrden === 'calificacion') {
            const califA = a.promedioCalificacion || 0;
            const califB = b.promedioCalificacion || 0;
            return califB - califA;
        }
        return 0;
    });

    return (
        <div className="bg-crema-fondo min-h-screen dark:bg-dark-fondo transition-colors duration-500 flex flex-col font-inter text-navy-letter dark:text-gray-100 font-medium">
            <Navbar estaAutenticado={true} />

            <main className="flex-grow py-10 px-4 sm:px-10 max-w-7xl mx-auto w-full">
                
                {/* --- SECCIÓN ENCABEZADO --- */}
                <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-8 text-left">
                    <div>
                        <h2 className="font-cormorant text-3xl font-bold tracking-tight">Biblioteca</h2>
                        <p className="text-gray-500 dark:text-gray-400 text-sm mt-0.5">
                            Explora todos los libros disponibles
                        </p>
                    </div>

                    <AccionRecomendada href="/registrarLibro" variante="primario">
                        + Añadir Libro
                    </AccionRecomendada>
                </div>

                {/* --- BARRA DE CONTROL Y FILTROS --- */}
                <div className="bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-xl p-4 mb-8 flex flex-col sm:flex-row justify-between items-center gap-4 text-xs shadow-sm transition-colors duration-300">
                    
                    {/* Selector de Criterio de Ordenamiento */}
                    <div className="flex items-center gap-2 self-start sm:self-auto">
                        <span className="text-gray-400">Ordenar por:</span>
                        <select 
                            value={criterioOrden}
                            onChange={(e) => setCriterioOrden(e.target.value)}
                            className="bg-gray-50 dark:bg-white/5 border border-gray-200 dark:border-white/10 rounded-lg px-2.5 py-1.5 font-medium text-gray-700 dark:text-gray-300 focus:outline-none"
                        >
                            <option value="titulo">Título</option>
                            <option value="autor">Autor</option>
                            <option value="calificacion">Calificación</option>
                        </select>
                    </div>

                    {/* Grupo de Control de Layout y Contador */}
                    <div className="flex items-center justify-between sm:justify-end w-full sm:w-auto gap-6">
                        
                        {/* Selector de Tipo de Vista (Grid / Lista) */}
                        <div className="flex items-center gap-2">
                            <span className="text-gray-400">Vista:</span>
                            <div className="flex bg-gray-50 dark:bg-white/5 rounded-lg p-0.5 border border-gray-200 dark:border-white/10">
                                
                                {/* Opción: Vista Cuadrícula */}
                                <button 
                                    onClick={() => setVistaGrid(true)}
                                    className={`p-1.5 rounded-md transition-colors ${vistaGrid ? 'bg-white dark:bg-white/10 shadow-sm text-navy-letter dark:text-white' : 'text-gray-400 hover:text-gray-600'}`}
                                >
                                    <img 
                                      src={cuadricula} 
                                      alt="Vista cuadrícula" 
                                      className="h-4 w-4 object-contain transition-all duration-300 dark:invert" 
                                    />
                                </button>

                                {/* Opción: Vista Lista */}
                                <button 
                                    onClick={() => setVistaGrid(false)}
                                    className={`p-1.5 rounded-md transition-colors ${!vistaGrid ? 'bg-white dark:bg-white/10 shadow-sm text-navy-letter dark:text-white' : 'text-gray-400 hover:text-gray-600'}`}
                                >
                                    <img 
                                      src={lista} 
                                      alt="Vista Lista" 
                                      className="h-4 w-4 object-contain transition-all duration-300 dark:invert" 
                                    />
                                </button>
                            </div>
                        </div>

                        {/* Métrica del inventario */}
                        <div className="flex items-center gap-1.5 text-gray-400 font-medium">                            
                            <span>{libros.length} libros en total</span>
                        </div>
                    </div>
                </div>

                {/* --- DISPLAY DE RESULTADOS --- */}
                {libros.length === 0 ? (
                    <div className="text-center py-20 text-gray-500 font-medium">
                        No hay libros disponibles en el catálogo todavía.
                    </div>
                ) : vistaGrid ? (
                    /* MODALIDAD A: VISTA EN CUADRÍCULA (GRID) */
                    <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-x-6 gap-y-8 text-left">
                        {librosOrdenados.map((libro) => (
                            <Link 
                                to={`/libro/${libro.idLibro}`} 
                                state={{ libroData: libro }}
                                key={libro.idLibro} 
                                className="flex flex-col group cursor-pointer no-underline text-current select-none"
                            >
                                {/* Portada e Indicador de Calificación */}
                                <div className="w-full aspect-[2/3] rounded-xl overflow-hidden relative shadow-sm group-hover:shadow-md transition-shadow duration-300 bg-gray-100 dark:bg-white/5">
                                    <img 
                                        src={obtenerUrlImagen(libro.imagen)} 
                                        alt={libro.titulo}
                                        className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                                        loading="lazy"
                                    />
                                    <div className="absolute top-2.5 right-2.5 bg-black/70 backdrop-blur-xs text-white text-[10px] font-bold px-2 py-0.5 rounded-full flex items-center gap-1">
                                        <span className="text-yellow-400 text-xs leading-none">★</span>
                                        <span>{libro.promedioCalificacion != null ? Number(libro.promedioCalificacion).toFixed(1) : '0.0'}</span>
                                    </div>
                                </div>

                                {/* Textos informativos inferiores */}
                                <div className="mt-3 flex flex-col gap-0.5">
                                    <h4 className="font-medium text-sm line-clamp-1 group-hover:text-[#d4a373] transition-colors">
                                        {libro.titulo}
                                    </h4>
                                    <p className="text-gray-400 text-xs font-medium line-clamp-1">
                                        {libro.autor?.nombreAutor || 'Autor desconocido'}
                                    </p>
                                </div>
                            </Link>
                        ))}
                    </div>
                ) : (
                    /* MODALIDAD B: VISTA EN LISTA COMPACTA */
                    <div className="flex flex-col gap-3 text-left">
                        {librosOrdenados.map((libro) => (
                            <Link 
                                to={`/libro/${libro.idLibro}`} 
                                key={libro.idLibro} 
                                state={{ libroData: libro }}                                
                                className="bg-white dark:bg-dark-borde p-4 rounded-xl border border-amber-900/5 dark:border-white/5 flex items-center gap-4 hover:shadow-xs transition-shadow no-underline text-current cursor-pointer select-none"
                            >
                                <img src={obtenerUrlImagen(libro.imagen)} alt={libro.titulo} className="w-12 h-16 object-cover rounded-lg" />
                                <div className="flex-grow">
                                    <h4 className="font-bold text-sm">{libro.titulo}</h4>
                                    <p className="text-gray-400 text-xs">{libro.autor?.nombreAutor || 'Autor desconocido'}</p>
                                </div>
                                <div className="bg-amber-400/10 text-amber-600 dark:text-amber-400 px-3 py-1 rounded-full font-bold text-xs flex items-center gap-1">
                                    ★ {libro.promedioCalificacion != null ? Number(libro.promedioCalificacion).toFixed(1) : '0.0'}
                                </div>
                            </Link>
                        ))}
                    </div>
                )}

            </main>

            <Footer estaAutenticado={true} />
        </div>
    );
};

export default Biblioteca;
