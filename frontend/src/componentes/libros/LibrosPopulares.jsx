import React from 'react';
import NavLink from '../navegacion/navbar/NavLink';

/**
 * IMPORTACIÓN DE ASSETS
 * Se importan las imágenes localmente para que el empaquetador (Vite) 
 * genere los hashes de archivo correspondientes y optimice la carga.
 */
import imgOrgullo from '../../estilos/img/imagenes-ejemplo/orgulloYprejuicio.png';
import img1984 from '../../estilos/img/imagenes-ejemplo/1984.png';
import imgCienAnos from '../../estilos/img/imagenes-ejemplo/cienAñosDeSoledad.png';

/**
 * COMPONENTE: LibroCard
 * Renderiza la tarjeta individual de un libro.
 * @param {string} imagen - Ruta de la portada procesada por Vite.
 * @param {string} titulo - Nombre del libro.
 * @param {string} autor - Creador de la obra.
 * @param {string} rating - Puntaje promedio (ej: "4.5").
 * @param {string} resenas - Cantidad total de opiniones.
 */
const LibroCard = ({ imagen, titulo, autor, rating, resenas }) => (
    <div className="bg-white dark:bg-dark-borde rounded-2xl overflow-hidden border border-gray-100 dark:border-white/10 shadow-sm hover:shadow-xl transition-all duration-300 group flex flex-col">
        
        {/* CONTENEDOR DE IMAGEN: Altura fija (h-110) para uniformidad en el grid */}
        <div className="h-110 overflow-hidden bg-gray-200">
            <img 
                src={imagen} 
                alt={titulo} 
                className="w-full h-full object-cover group-hover:scale-120 transition-transform duration-700"
            />
        </div>      
    
        {/* CUERPO DE LA TARJETA: Contenedor flexible para alinear el footer al fondo */}
        <div className="p-6 flex flex-col flex-grow text-left">
            
            {/* Título interactivo: Reutiliza el componente NavLink para consistencia */}
            <NavLink href="/">{titulo}</NavLink>
            <p className="text-gray-500 dark:text-gray-400 font-inter text-sm mb-4">{autor}</p>
            
            {/* FOOTER DE TARJETA: Separado por una línea sutil (border-t) */}
            <div className="mt-auto pt-4 border-t border-gray-50 dark:border-white/5 flex justify-between items-center">
                {/* Rating con estrella dorada */}
                <div className="flex items-center gap-1.5">
                    <span className="text-gold-books-2 text-lg">★</span>
                    <span className="font-bold text-navy-letter dark:text-gray-200">{rating}</span>
                </div>
                {/* Contador de reseñas en formato tracking-widest (estilo editorial) */}
                <span className="text-gray-400 dark:text-gray-500 text-xs font-inter uppercase tracking-widest">
                    {resenas} reseñas
                </span>
            </div>
        </div>
    </div>
);

/**
 * COMPONENTE PRINCIPAL: LibrosPopulares
 * Muestra una sección de libros destacados que varía según el estado de sesión.
 * @param {boolean} estaAutenticado - Define si el usuario ha iniciado sesión.
 */
const LibrosPopulares = ({ estaAutenticado = false }) => {
    // DATA MOCK: Lista estática de libros para la fase inicial del proyecto
    const libros = [
        { id: 1, titulo: "Orgullo y Prejuicio", autor: "Jane Austen", rating: "4.7", resenas: "312", imagen: imgOrgullo },
        { id: 2, titulo: "1984", autor: "George Orwell", rating: "4.6", resenas: "243", imagen: img1984 },
        { id: 3, titulo: "Cien Años de Soledad", autor: "Gabriel Garcia Marquez", rating: "4.9", resenas: "389", imagen: imgCienAnos }
    ];

    return (
        <section className="py-20 px-10 bg-crema-fondo dark:bg-dark-fondo transition-colors duration-300">
            <div className="max-w-6xl mx-auto">
                
                {/* ENCABEZADO DE SECCIÓN: Título dinámico y SVG de tendencia */}
                <div className="flex items-center gap-3 mb-12 group w-fit">
                    <div className="p-2 rounded-lg bg-gold-books-2/10 text-gold-books-2">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                        </svg>
                    </div>
                    <h2 className="font-cormorant text-3xl font-bold text-navy-letter dark:text-gray-100">
                        {estaAutenticado ? "Recomendados para ti" : "Libros populares esta semana"}
                    </h2>
                </div>

                {/* GRID RESPONSIVO: 1 col (móvil), 2 cols (tablet), 3 cols (desktop) */}
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-10">
                    {libros.map((libro) => (
                        <LibroCard key={libro.id} {...libro} />
                    ))}
                </div>
            </div>
        </section>
    );
};

export default LibrosPopulares;