import React from 'react';

/**
 * IMPORTACIÓN DE COMPONENTES ESTRUCTURALES
 * Se organizan por jerarquía: Navegación, Héroe y Secciones de Contenido.
 */
import Navbar from '../componentes/navegacion/navbar/Navbar';
import Footer from '../componentes/navegacion/footer/Footer';
import Hero from '../componentes/comunes/Hero'; 
import LibrosPopulares from '../componentes/libros/LibrosPopulares';
import ResenasRecientes from '../componentes/comentarios/ResenasRecientes';

/**
 * COMPONENTE: PaginaAterrizaje
 * Punto de entrada principal para usuarios no autenticados (Landing Page).
 * * Este componente actúa como un 'Layout' que define el flujo visual:
 * 1. Navbar (Estatica/Sticky)
 * 2. Hero (Impacto visual inicial)
 * 3. Libros Populares (Muestra de catálogo)
 * 4. Reseñas Recientes (Prueba social y comunidad)
 * 5. Footer (Cierre y enlaces legales/sociales)
 */
const PaginaAterrizaje = () => {
  return (
    /* CONTENEDOR PRINCIPAL 
       'bg-crema-fondo' mantiene la estética de papel/diario.
       'min-h-screen' asegura que el fondo cubra todo el alto del navegador.
    */
    <div className="bg-crema-fondo min-h-screen dark:bg-dark-fondo transition-colors duration-500">
      
      {/* NAVEGACIÓN: Definida explícitamente como no autenticada para invitados */}
      <Navbar estaAutenticado={false} />
      
      <main>
          {/* SECCIÓN HERO: Llamada a la acción principal (CTA) */}
          <Hero />   

          {/* SECCIÓN CATÁLOGO: Muestra tendencias globales (modo invitado) */}
          <LibrosPopulares estaAutenticado={false} />  

          {/* SECCIÓN SOCIAL: Feed de actividad de la comunidad */}
          <ResenasRecientes />     
      </main>

      {/* PIE DE PÁGINA: Muestra opciones de registro/invitado */}
      <Footer estaAutenticado={false} />
      
    </div>
  );
};

export default PaginaAterrizaje;