import React, { useContext, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { ContextoSesion } from '../contexto/Sesion';

import subir from '../estilos/img/iconos/subir.png'

import Navbar from '../componentes/navegacion/navbar/Navbar';
import Footer from '../componentes/navegacion/footer/Footer';


/**
 * COMPONENTE: RegistrarLibro
 * Vista completa con selección de archivo local para vista previa
 * y menús desplegables para Autor, Género y Editorial.
 */
const RegistrarLibro = () => {
    const { token } = useContext(ContextoSesion);

    // Opciones locales para popular los dropdowns visualmente
    const listaGeneros = [
        { id: 1, nombre: 'Fantasía' },
        { id: 2, nombre: 'Ciencia Ficción' },
        { id: 3, nombre: 'Drama' }
    ];

    const listaAutores = [
        { id: 1, nombre: 'Patrick Rothfuss' },
        { id: 2, nombre: 'Brandon Sanderson' },
        { id: 3, nombre: 'J.K. Rowling' }
    ];

    const listaEditoriales = [
        { id: 1, nombre: 'Pearson' },
        { id: 2, nombre: 'Nova' },
        { id: 3, nombre: 'Salamandra' }
    ];

    // ESTADOS PARA EL FORMULARIO
    const [titulo, setTitulo] = useState('');
    const [idAutor, setIdAutor] = useState('');
    const [ano, setAno] = useState('2007');
    const [paginas, setPaginas] = useState('662');
    const [idGenero, setIdGenero] = useState('');
    const [idEditorial, setIdEditorial] = useState('');
    const [isbn, setIsbn] = useState('');
    const [descripcionBreve, setDescripcionBreve] = useState('');
    const [sinopsis, setSinopsis] = useState('');
    
    // ESTADOS PARA ARCHIVO DE IMAGEN Y VISTA PREVIA
    const [portada, setPortada] = useState(null);
    const [vistaPrevia, setVistaPrevia] = useState(null);

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    // MANEJADOR DE ARCHIVO LOCAL (Genera vista previa sin subir al servidor aún)
    const handleCambioImagen = (e) => {
        const archivo = e.target.files[0];
        if (archivo) {
            setPortada(archivo);
            setVistaPrevia(URL.createObjectURL(archivo));
        }
    };

    // MANEJADOR DEL ENVÍO (Estructura los datos para cuando acoples tu lógica)
    const handleEnviar = (e) => {
        e.preventDefault();

        const datosFront = {
            titulo,
            idAutor: Number(idAutor),
            ano: Number(ano),
            paginas: Number(paginas),
            idGenero: Number(idGenero),
            idEditorial: Number(idEditorial),
            isbn,
            sinopsis,
            archivoImagen: portada 
        };

        console.log("Datos capturados en el cliente:", datosFront);
        alert("Campos validados:)");
    };

    return (
        <div className="bg-crema-fondo min-h-screen dark:bg-dark-fondo transition-colors duration-500 flex flex-col">
            <Navbar estaAutenticado={true} />

            <main className="flex-grow">
                <section className="py-12 px-4 sm:px-10 max-w-4xl mx-auto text-navy-letter dark:text-gray-100">
                    
                    {/* Encabezado */}
                    <div className="flex flex-col text-left mb-6">
                        <h2 className="font-cormorant text-3xl font-bold">Añadir nuevo libro</h2>
                        <p className="text-gray-500 dark:text-gray-400 font-inter text-sm">
                            Comparte un libro con la comunidad
                        </p>
                    </div>

                    {/* Contenedor Principal del Formulario */}
                    <form onSubmit={handleEnviar} className="bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-2xl p-8 shadow-sm flex flex-col md:flex-row gap-8 text-left transition-colors duration-300">
                        
                        {/* COLUMNA IZQUIERDA: Selección de archivo e Imagen local */}
                        <div className="w-full md:w-1/3 flex flex-col items-center md:items-start">                           
                            <span className="font-inter text-xs font-bold text-gray-700 dark:text-gray-300 mb-2 self-start">
                                Portada del libro
                            </span>
                            
                            <label className="w-full h-80 min-h-[320px] border-2 border-dashed border-amber-900/20 dark:border-white/20 rounded-xl flex flex-col items-center justify-center p-4 text-center cursor-pointer hover:bg-gray-50 dark:hover:bg-white/5 transition-all relative overflow-hidden group">
                                <input 
                                    type="file" 
                                    accept="image/png, image/jpeg" 
                                    className="hidden" 
                                    onChange={handleCambioImagen}
                                />
                                {vistaPrevia ? (
                                    <img src={vistaPrevia} alt="Vista previa" className="w-full h-full object-cover absolute inset-0" />
                                ) : (
                                    <div className="flex flex-col items-center justify-center text-gray-400">
                                        <img 
                                            src={subir} 
                                            alt="Icono de cargar archivo" 
                                            className="h-10 w-10 object-contain transition-all duration-300 dark:invert" 
                                        />
                                        <span></span>
                                        <span className="font-inter text-xs text-gray-600 dark:text-gray-300 font-medium">Haz clic para subir la portada</span>
                                    </div>
                                )}
                            </label>
                            <p className="text-[10px] text-gray-400 mt-2 leading-tight">
                                Formatos: JPG, PNG. Recomendado: 300x450px
                            </p>
                        </div>

                        {/* COLUMNA DERECHA: Campos de texto y Dropdowns */}
                        <div className="w-full md:w-2/3 flex flex-col gap-4 font-inter text-sm">
                            
                            {/* Título */}
                            <div className="flex flex-col gap-1">
                                <label className="font-medium text-xs">Título *</label>
                                <input 
                                    type="text" required placeholder="El nombre del viento" value={titulo} onChange={(e) => setTitulo(e.target.value)}
                                    className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
                                />
                            </div>

                            {/* DROPDOWN: Autor */}
                            <div className="flex flex-col gap-1">
                                <label className="font-medium text-xs">Autor *</label>
                                <select 
                                    required value={idAutor} onChange={(e) => setIdAutor(e.target.value)}
                                    className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-white dark:bg-dark-borde text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button"
                                >
                                    <option value="" disabled>Selecciona un autor</option>
                                    {listaAutores.map(aut => (
                                        <option key={aut.id} value={aut.id}>{aut.nombre}</option>
                                    ))}
                                </select>
                            </div>

                            {/* Año y Páginas */}
                            <div className="grid grid-cols-2 gap-4">
                                <div className="flex flex-col gap-1">
                                    <label className="font-medium text-xs">Año de publicación *</label>
                                    <input 
                                        type="number" required value={ano} onChange={(e) => setAno(e.target.value)}
                                        className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
                                    />
                                </div>
                                <div className="flex flex-col gap-1">
                                    <label className="font-medium text-xs">Número de páginas *</label>
                                    <input 
                                        type="number" required value={paginas} onChange={(e) => setPaginas(e.target.value)}
                                        className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
                                    />
                                </div>
                            </div>

                            {/* DROPDOWN: Género */}
                            <div className="flex flex-col gap-1">
                                <label className="font-medium text-xs">Género *</label>
                                <select 
                                    required value={idGenero} onChange={(e) => setIdGenero(e.target.value)}
                                    className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-white dark:bg-dark-borde text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button"
                                >
                                    <option value="" disabled>Selecciona un género</option>
                                    {listaGeneros.map(gen => (
                                        <option key={gen.id} value={gen.id}>{gen.nombre}</option>
                                    ))}
                                </select>
                            </div>

                            {/* DROPDOWN: Editorial e ISBN */}
                            <div className="grid grid-cols-2 gap-4">
                                <div className="flex flex-col gap-1">
                                    <label className="font-medium text-xs">Editorial *</label>
                                    <select 
                                        required value={idEditorial} onChange={(e) => setIdEditorial(e.target.value)}
                                        className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-white dark:bg-dark-borde text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button"
                                    >
                                        <option value="" disabled>Selecciona una editorial</option>
                                        {listaEditoriales.map(ed => (
                                            <option key={ed.id} value={ed.id}>{ed.nombre}</option>
                                        ))}
                                    </select>
                                </div>
                                <div className="flex flex-col gap-1">
                                    <label className="font-medium text-xs">ISBN</label>
                                    <input 
                                        type="text" placeholder="978-0756404079" value={isbn} onChange={(e) => setIsbn(e.target.value)}
                                        className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
                                    />
                                </div>
                            </div>

                            {/* Sinopsis */}
                            <div className="flex flex-col gap-1">
                                <label className="font-medium text-xs">Sinopsis *</label>
                                <textarea 
                                    required placeholder="Una sinopsis detallada del libro..." value={sinopsis} onChange={(e) => setSinopsis(e.target.value)}
                                    className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent h-32 resize-none focus:outline-none focus:ring-1 focus:ring-gold-button"
                                />
                            </div>

                            {/* Botones de acción */}
                            <div className="flex gap-4 mt-4 border-t border-gray-100 dark:border-white/5 pt-4">
                                <button 
                                    type="submit"
                                    className="bg-gold-button dark:bg-navy-button text-white px-6 py-2.5 rounded-lg flex items-center gap-2 font-medium hover:opacity-90 transition-opacity cursor-pointer"
                                >                                    
                                    + Añadir libro
                                </button>
                                <button 
                                    type="button"
                                    className="bg-amber-900/5 text-gray-700 dark:bg-white/5 dark:text-gray-300 px-6 py-2.5 rounded-lg font-medium hover:bg-amber-900/10 dark:hover:bg-white/10 transition-colors"
                                >
                                    Cancelar
                                </button>
                            </div>
                        </div>
                    </form>

                    {/* Bloque de Consejos Inferior */}
                    <div className="mt-6 bg-orange-50/50 dark:bg-white/5 border border-amber-900/5 dark:border-white/5 rounded-2xl p-6 text-left text-xs text-amber-900/80 dark:text-gray-300 font-inter">
                        <div className="flex items-center gap-2 font-bold mb-3 text-amber-900 dark:text-amber-200">                            
                            Consejos para añadir libros
                        </div>
                        <ul className="list-disc list-inside flex flex-col gap-1.5 pl-1 text-gray-600 dark:text-gray-400">
                            <li>Verifica que el libro no esté ya en la biblioteca antes de añadirlo.</li>
                            <li>Usa información precisa y completa para ayudar a otros lectores.</li>
                            <li>La portada debe ser de buena calidad y representar la edición correcta.</li>
                            <li>Escribe una sinopsis clara sin revelar spoilers importantes.</li>
                        </ul>
                    </div>

                </section>
            </main>

            <Footer estaAutenticado={true} />
        </div>
    );
};

export default RegistrarLibro;