import React, { useEffect, useState } from 'react';
import EnlaceRuta from '../navegacion/EnlaceRuta';
import { obtenerLibrosPopulares } from '../../api/Libros';

const obtenerUrlImagen = (ruta) => {
    if (!ruta) return 'https://via.placeholder.com/300x450?text=Sin+Portada';
    if (ruta.startsWith('http')) return ruta;
    return `http://localhost:8080${ruta}`;
};

/**
 * COMPONENTE: LibroCard
 * Renderiza la tarjeta individual de un libro (portada y título clicables).
 */
const LibroCard = ({ libro, imagen, titulo, autor, rating, resenas }) => (
    <EnlaceRuta
        to={`/libro/${libro.idLibro}`}
        state={{ libroData: libro }}
        className="bg-white dark:bg-dark-borde rounded-2xl overflow-hidden border border-gray-100 dark:border-white/10 shadow-sm hover:shadow-xl transition-all duration-300 group flex flex-col no-underline text-current"
    >
        <div className="h-110 overflow-hidden bg-gray-200">
            <img 
                src={imagen} 
                alt={titulo} 
                className="w-full h-full object-cover group-hover:scale-120 transition-transform duration-700"
            />
        </div>      
    
        <div className="p-6 flex flex-col flex-grow text-left">
            <h3 className="font-cormorant text-xl font-bold text-navy-letter dark:text-gray-100 group-hover:text-gold-button transition-colors duration-200">
                {titulo}
            </h3>
            <p className="text-gray-500 dark:text-gray-400 font-inter text-sm mb-4">{autor}</p>
            
            <div className="mt-auto pt-4 border-t border-gray-50 dark:border-white/5 flex justify-between items-center">
                <div className="flex items-center gap-1.5">
                    <span className="text-gold-button text-lg">★</span>
                    <span className="font-bold text-navy-letter dark:text-gray-200">{rating}</span>
                </div>
                <span className="text-gray-400 dark:text-gray-500 text-xs font-inter uppercase tracking-widest">
                    {resenas} reseñas
                </span>
            </div>
        </div>
    </EnlaceRuta>
);

/**
 * COMPONENTE PRINCIPAL: LibrosPopulares
 * Muestra libros destacados obtenidos desde la base de datos.
 */
const LibrosPopulares = ({ estaAutenticado = false }) => {
    const [libros, setLibros] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const cargarLibros = async () => {
            try {
                setCargando(true);
                setError(null);
                const data = await obtenerLibrosPopulares(3);
                setLibros(data);
            } catch (err) {
                console.error('Error al cargar libros populares:', err);
                setError('No se pudieron cargar los libros populares.');
            } finally {
                setCargando(false);
            }
        };

        cargarLibros();
    }, []);

    return (
        <section className="py-20 px-10 bg-crema-fondo dark:bg-dark-fondo transition-colors duration-300">
            <div className="max-w-6xl mx-auto">
                <div className="flex items-center gap-3 mb-12 group w-fit">
                    <div className="p-2 rounded-lg bg-gold-button/10 text-gold-button">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                        </svg>
                    </div>
                    <h2 className="font-cormorant text-3xl font-bold text-navy-letter dark:text-gray-100">
                        {estaAutenticado ? "Recomendados para ti" : "Libros populares esta semana"}
                    </h2>
                </div>

                {cargando && (
                    <p className="text-gray-500 dark:text-gray-400 font-inter">Cargando libros...</p>
                )}

                {error && (
                    <p className="text-red-500 font-inter">{error}</p>
                )}

                {!cargando && !error && libros.length === 0 && (
                    <p className="text-gray-500 dark:text-gray-400 font-inter">Aún no hay libros en el catálogo.</p>
                )}

                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-10">
                    {libros.map((libro) => (
                        <LibroCard
                            key={libro.idLibro}
                            libro={libro}
                            imagen={obtenerUrlImagen(libro.imagen)}
                            titulo={libro.titulo}
                            autor={libro.nombreAutor || 'Autor desconocido'}
                            rating={libro.promedioCalificacion != null ? Number(libro.promedioCalificacion).toFixed(1) : '0.0'}
                            resenas={libro.totalResenas ?? 0}
                        />
                    ))}
                </div>
            </div>
        </section>
    );
};

export default LibrosPopulares;
