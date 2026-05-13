import React from "react";
import Logo from './Logo';
import AccionRecomendada from "./AccionRecomendada";

/**
 * IMPORTACIÓN DE RECURSOS VISUALES
 * Se utilizan iconos específicos para cada beneficio de la plataforma.
 */
import libro from '../../estilos/img/iconos/libro.png'; 
import estrellas from '../../estilos/img/iconos/estrellas.png'; 
import estrella from '../../estilos/img/iconos/estrella.png'; 
import personas from '../../estilos/img/iconos/personas.png'; 

/**
 * COMPONENTE: FeatureCard
 * Representa los beneficios clave (Value Propositions) de la app.
 * @param {string} icono - Ruta de la imagen del icono.
 * @param {string} titulo - Encabezado del beneficio.
 * @param {string} descripcion - Texto explicativo corto.
 * @param {string} bgIcono - Clase de Tailwind para el fondo del icono.
 */
const FeatureCard = ({ icono, titulo, descripcion, bgIcono }) => (
    <div className="bg-white dark:bg-dark-borde p-8 rounded-2xl border border-gold-books-2/20 dark:border-white/5 shadow-sm hover:shadow-md transform hover:-translate-y-1 flex flex-col items-start text-left transition-all duration-300">
        
        {/* Contenedor del Icono con fondo dinámico */}
        <div className={`${bgIcono} p-4 rounded-xl mb-4 transition-colors duration-300`}>
            <img 
              src={icono} 
              alt="icono" 
              className="h-8 w-auto dark:brightness-200" 
            />
        </div>

        <h3 className="text-xl font-bold text-navy-letter dark:text-gray-100 mb-2">
            {titulo}
        </h3>

        <p className="text-gray-500 dark:text-gray-400 text-sm font-inter leading-relaxed">
            {descripcion}
        </p>
    </div>
);

/**
 * COMPONENTE PRINCIPAL: Hero
 * Sección de impacto que presenta la propuesta de valor principal.
 */
const Hero = () => {
    return(
        <section className="bg-crema-fondo dark:bg-dark-fondo py-20 px-10 transition-colors duration-300">
            <div className="flex flex-col items-center text-center gap-5">

                {/* BADGE DE BIENVENIDA: Micro-detalle editorial */}
                <div className="bg-[#f3e9dc] dark:bg-[#4A4E54] dark:text-white text-gold-books-2 px-4 py-2 rounded-full text-xs font-inter mb-6 tracking-wider flex items-center gap-2 border border-transparent dark:border-gold-books-2/10">
                    <img
                        src={estrellas} 
                        alt="Estrellas"                 
                        className="h-4 w-auto dark:brightness-200" 
                    />
                    Tu diario literario
                </div>

                {/* TÍTULO PRINCIPAL (H1): 
                    Implementa un degradado (gradient) que combina tonos metálicos y tierra 
                    para evocar una sensación de elegancia y lectura clásica.
                */}
                <h1 className="text-7xl font-bold bg-gradient-to-r from-[#4A4E54] dark:from-gray-100 via-[#A89276] to-[#C9A675] bg-clip-text text-transparent">
                    Comparte tu amor por los libros
                </h1>

                {/* SUBTITULADO: Texto de apoyo con jerarquía visual secundaria */}
                <div className="text-gray-500 dark:text-gray-400 font-inter text-md text-center flex flex-col gap-2">
                    <p>Descubre, reseña y conecta con una comunidad apasionada por la lectura.</p>                     
                    <p>Tu siguiente gran lectura te está esperando.</p>
                </div>

                {/* CTA (Call to Action): Botón principal de conversión */}
                <div className="mb-20 px-2 py-2">
                    <AccionRecomendada variante="primario" className="text-lg px-10 py-4 flex items-center gap-2">
                        Comenzar gratis →
                    </AccionRecomendada>
                </div>

                {/* GRID DE CARACTERÍSTICAS (Features): 
                    Organizado en 3 columnas en desktop y 1 en móvil. 
                    Utiliza el componente modular FeatureCard.
                */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-8 w-full">
                    <FeatureCard 
                        icono={libro} 
                        titulo="Registra tus lecturas" 
                        descripcion="Lleva un diario de todos los libros que has leído y los que quieres leer." 
                        bgIcono="bg-orange-100 dark:bg-[#4a4444]"
                    />
                    <FeatureCard 
                        icono={estrella} 
                        titulo="Comparte reseñas" 
                        descripcion="Califica y escribe sobre tus libros favoritos. Ayuda a otros a encontrar su próxima lectura." 
                        bgIcono="bg-yellow-100 dark:bg-[#4a4444]"
                    />
                    <FeatureCard 
                        icono={personas} 
                        titulo="Conecta con lectores" 
                        descripcion="Sigue a otros amantes de la lectura y descubre nuevas recomendaciones personalizadas." 
                        bgIcono="bg-blue-100 dark:bg-[#4a4444]"
                    />
                </div>
            </div>
        </section>
    )
};

export default Hero;