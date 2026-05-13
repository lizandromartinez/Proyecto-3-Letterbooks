import React from 'react';
import Cita from './Cita';
import like from '../../estilos/img/iconos/like.png'; 
import comentarioIcon from '../../estilos/img/iconos/comentario.png'; 
import mujerImg from '../../estilos/img/imagenes-ejemplo/mujer_random.png';
import libroCienAnos from '../../estilos/img/imagenes-ejemplo/cienAñosDeSoledad.png';

/**
 * COMPONENTE: ResenaCard
 * Representación visual de una reseña individual.
 * @param {Object} resena - Objeto con toda la información de la reseña.
 */
const ResenaCard = ({ resena }) => {
    return (
        <div className="bg-white dark:bg-dark-borde p-8 rounded-3xl border border-gray-100 dark:border-white/10 shadow-sm hover:shadow-md transition-shadow duration-300 max-w-3xl mx-auto mb-10">
            
            {/* CABECERA: Información del usuario y fecha */}
            <div className="flex justify-between items-start mb-6">
                <div className="flex items-center gap-4">
                    <img 
                        src={resena.userImg} 
                        alt={resena.usuario} 
                        className="w-12 h-12 rounded-full border-2 border-gold-books-2/20 object-cover" 
                    />
                    <div className="text-left">
                        <h4 className="font-bold text-navy-letter dark:text-white leading-tight">
                            {resena.usuario}
                        </h4>
                        <p className="text-gray-400 text-sm">@{resena.arroba}</p>
                    </div>
                </div>
                <span className="text-gray-400 text-xs font-inter">{resena.fecha}</span>
            </div>

            {/* INFO LIBRO: Enlace visual al detalle del libro reseñado */}
            <a 
                href={`/libro/${resena.id}`} 
                className="flex gap-4 mb-6 p-4 bg-[#dedfe0] dark:bg-white/5 rounded-2xl border border-gold-books-2/5 hover:bg-[#c7c7c7] transition-all duration-300"
            >
                <img 
                    src={resena.libroImg} 
                    alt={resena.libro} 
                    className="w-14 h-20 object-cover rounded-lg shadow-sm" 
                />
                <div className="text-left flex flex-col justify-center">
                    <h5 className="font-cormorant font-bold text-lg dark:text-gray-100 leading-tight">
                        {resena.libro}
                    </h5>
                    <p className="text-gray-500 text-xs mb-2">{resena.autor}</p>
                    {/* Renderizado dinámico de estrellas (Rating) */}
                    <div className="flex text-gold-books-2 text-xs">
                        {"★".repeat(resena.estrellas)}{"☆".repeat(5 - resena.estrellas)}
                    </div>
                </div>
            </a>

            {/* CUERPO: Comentario principal del usuario */}
            <p className="text-black dark:text-white text-left mb-6 font-inter leading-relaxed">
                {resena.comentario}
            </p>

            {/* SECCIÓN DE CITAS: Mapeo del componente modular 'Cita' */}
            <div className="space-y-2 mb-6">
                {resena.citas && resena.citas.map((item, index) => (
                    <Cita 
                        key={index} 
                        texto={item.texto} 
                        pagina={item.pagina} 
                    />
                ))}
            </div>

            {/* FOOTER: Botones de interacción (Like y Comentarios) */}
            <div className="flex gap-5 mt-6 pt-4 border-t border-gray-50 dark:border-white/5">
                {/* Botón Like */}
                <button className="flex items-center gap-1.5 text-gray-400 hover:text-red-500 transition-colors group">
                    <img
                        src={like} 
                        alt="Icono de Like" 
                        className="h-5 w-auto dark:invert dark:brightness-200 transition-all group-hover:scale-110"
                    />
                    <span className="text-xs font-bold">{resena.likes}</span>
                </button>
                
                {/* Botón Comentar */}
                <button className="flex items-center gap-1.5 text-gray-400 hover:text-navy-letter dark:hover:text-white transition-colors group">
                    <img
                        src={comentarioIcon} 
                        alt="Icono de Comentario" 
                        className="h-5 w-auto dark:invert dark:brightness-200 transition-all group-hover:scale-110"
                    />
                    <span className="text-xs font-bold">{resena.comments}</span>
                </button>
            </div>
        </div>
    );
};

/**
 * COMPONENTE PRINCIPAL: ReseñasRecientes
 * Gestiona el conjunto de datos y el layout de la sección.
 */
const ResenasRecientes = () => {
    // MOCK DATA: Simulación de datos provenientes de una Base de Datos
    const resenas = [
        {
            id: 1,
            usuario: "Ana Lee",
            arroba: "ana_lee",
            fecha: "12 mayo",
            userImg: mujerImg,    
            libro: "Cien años de soledad",
            autor: "Gabriel García Márquez",
            libroImg: libroCienAnos,
            estrellas: 5,
            comentario: "Una obra maestra absoluta. García Márquez teje una narrativa tan rica y compleja que te sumerge completamente en Macondo. Cada relectura revela nuevas capas de significado.",
            citas: [
                { texto: "Muchos años después, frente al pelotón de fusilamiento, el coronel Aureliano Buendía había de recordar aquella tarde remota...", pagina: "1" },
                { texto: "La vida no es sino una continua sucesión de oportunidades para sobrevivir.", pagina: "156" }
            ],
            likes: 3,
            comments: 1
        }
    ];

    return (
        <section className="py-20 px-10 bg-[#FDF8F3] dark:bg-dark-fondo transition-colors duration-300">
            <div className="max-w-4xl mx-auto text-left mb-10">
                <h2 className="font-cormorant text-3xl font-bold text-navy-letter dark:text-gray-100">
                    Reseñas recientes
                </h2>                
            </div>
            
            {/* Iteración sobre el arreglo de reseñas */}
            {resenas.map(resena => (
                <ResenaCard key={resena.id} resena={resena} />
            ))}
        </section>
    );
};

export default ResenasRecientes;