import React, { useContext, useState, useEffect } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { ContextoSesion } from '../contexto/Sesion';

import subir from '../estilos/img/iconos/subir.png';

import Navbar from '../componentes/navegacion/navbar/Navbar';
import Footer from '../componentes/navegacion/footer/Footer';
import { 
    obtenerAutores, 
    obtenerGeneros, 
    obtenerEditoriales, 
    subirPortada, 
    registrarLibro,
    crearAutor,
    crearGenero,
    crearEditorial
} from '../api/Libros';

/**
 * COMPONENTE: RegistrarLibro
 * Vista completa con selección de archivo local para vista previa
 * y menús desplegables para Autor, Género y Editorial con creación rápida.
 */
const RegistrarLibro = () => {
    const { token } = useContext(ContextoSesion);
    const navigate = useNavigate();

    // ESTADOS PARA EL FORMULARIO
    const [listaGeneros, setListaGeneros] = useState([]);
    const [listaAutores, setListaAutores] = useState([]);
    const [listaEditoriales, setListaEditoriales] = useState([]);

    const [titulo, setTitulo] = useState('');
    const [idAutor, setIdAutor] = useState('');
    const [ano, setAno] = useState('2007');
    const [paginas, setPaginas] = useState('662');
    const [idGenero, setIdGenero] = useState('');
    const [idEditorial, setIdEditorial] = useState('');
    const [isbn, setIsbn] = useState('');
    const [sinopsis, setSinopsis] = useState('');
    
    // ESTADOS PARA ARCHIVO DE IMAGEN Y VISTA PREVIA
    const [portada, setPortada] = useState(null);
    const [vistaPrevia, setVistaPrevia] = useState(null);

    // ESTADOS PARA CREACIÓN RÁPIDA DE METADATOS
    const [creandoAutor, setCreandoAutor] = useState(false);
    const [nuevoAutorNombre, setNuevoAutorNombre] = useState('');
    const [creandoGenero, setCreandoGenero] = useState(false);
    const [nuevoGeneroNombre, setNuevoGeneroNombre] = useState('');
    const [creandoEditorial, setCreandoEditorial] = useState(false);
    const [nuevoEditorialNombre, setNuevoEditorialNombre] = useState('');

    const [cargando, setCargando] = useState(false);
    const [error, setError] = useState('');

    // Fetch lists from backend
    const cargarDatos = async () => {
        try {
            const autores = await obtenerAutores(token);
            setListaAutores(autores);
            
            const generos = await obtenerGeneros(token);
            setListaGeneros(generos);

            const editoriales = await obtenerEditoriales(token);
            setListaEditoriales(editoriales);
        } catch (err) {
            console.error("Error al cargar datos del catálogo:", err);
        }
    };

    useEffect(() => {
        if (token) {
            cargarDatos();
        }
    }, [token]);

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    // MANEJADORES DE CREACIÓN DINÁMICA
    const handleCrearAutor = async () => {
        if (!nuevoAutorNombre.trim()) return;
        try {
            const nuevo = await crearAutor(nuevoAutorNombre, token);
            setListaAutores(prev => [...prev, nuevo]);
            setIdAutor(nuevo.idAutor);
            setNuevoAutorNombre('');
            setCreandoAutor(false);
        } catch (err) {
            setError(err.message || 'Error al crear el autor');
        }
    };

    const handleCrearGenero = async () => {
        if (!nuevoGeneroNombre.trim()) return;
        try {
            const nuevo = await crearGenero(nuevoGeneroNombre, token);
            setListaGeneros(prev => [...prev, nuevo]);
            setIdGenero(nuevo.idGenero);
            setNuevoGeneroNombre('');
            setCreandoGenero(false);
        } catch (err) {
            setError(err.message || 'Error al crear el género');
        }
    };

    const handleCrearEditorial = async () => {
        if (!nuevoEditorialNombre.trim()) return;
        try {
            const nuevo = await crearEditorial(nuevoEditorialNombre, token);
            setListaEditoriales(prev => [...prev, nuevo]);
            setIdEditorial(nuevo.idEditorial);
            setNuevoEditorialNombre('');
            setCreandoEditorial(false);
        } catch (err) {
            setError(err.message || 'Error al crear la editorial');
        }
    };

    // MANEJADOR DE ARCHIVO LOCAL
    const handleCambioImagen = (e) => {
        const archivo = e.target.files[0];
        if (archivo) {
            setPortada(archivo);
            setVistaPrevia(URL.createObjectURL(archivo));
        }
    };

    // MANEJADOR DEL ENVÍO
    const handleEnviar = async (e) => {
        e.preventDefault();
        setCargando(true);
        setError('');

        if (!idAutor && !nuevoAutorNombre) {
            setError('Por favor selecciona o crea un autor');
            setCargando(false);
            return;
        }
        if (!idGenero && !nuevoGeneroNombre) {
            setError('Por favor selecciona o crea un género');
            setCargando(false);
            return;
        }
        if (!idEditorial && !nuevoEditorialNombre) {
            setError('Por favor selecciona o crea una editorial');
            setCargando(false);
            return;
        }

        try {
            let rutaImagen = '';
            if (portada) {
                rutaImagen = await subirPortada(portada, token);
            }

            const datosLibro = {
                titulo,
                idAutor: Number(idAutor),
                ano: Number(ano),
                paginas: Number(paginas),
                idGenero: Number(idGenero),
                idEditorial: Number(idEditorial),
                isbn: isbn || null,
                sinopsis: sinopsis || null,
                imagen: rutaImagen || null
            };

            await registrarLibro(datosLibro, token);
            navigate("/biblioteca");
        } catch (err) {
            console.error("Error al registrar libro:", err);
            setError(err.message || 'Error al registrar el libro. Verifica los campos.');
        } finally {
            setCargando(false);
        }
    };

    const formularioInvalido =  !titulo.trim() || !idAutor || !ano || !paginas || 
                                !idGenero || !idEditorial || !isbn || !sinopsis || 
                                !portada || cargando;

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

                    {/* Mostrar mensaje de error si ocurre */}
                    {error && (
                        <div className="mb-4 p-4 text-sm text-red-700 bg-red-100 rounded-lg dark:bg-red-200 dark:text-red-800" role="alert">
                            {error}
                        </div>
                    )}

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
                                        <span className="font-inter text-xs text-gray-600 dark:text-gray-300 font-medium mt-2">Haz clic para subir la portada</span>
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
                                <div className="flex justify-between items-center h-4">
                                    <label className="font-medium text-xs">Autor *</label>
                                    <button 
                                        type="button" 
                                        onClick={() => setCreandoAutor(!creandoAutor)}
                                        className="text-xs text-[#d4a373] hover:underline font-semibold focus:outline-none"
                                    >
                                        {creandoAutor ? 'Seleccionar existente' : '+ Nuevo'}
                                    </button>
                                </div>
                                {creandoAutor ? (
                                    <div className="flex gap-2">
                                        <input 
                                            type="text" 
                                            placeholder="Nombre del nuevo autor"
                                            value={nuevoAutorNombre} 
                                            onChange={(e) => setNuevoAutorNombre(e.target.value)}
                                            className="flex-grow p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
                                        />
                                        <button 
                                            type="button"
                                            onClick={handleCrearAutor}
                                            className="bg-gold-button text-white px-4 py-2.5 rounded-lg hover:opacity-90 transition-opacity font-medium text-xs"
                                        >
                                            Guardar
                                        </button>
                                    </div>
                                ) : (
                                    <select 
                                        required value={idAutor} onChange={(e) => setIdAutor(e.target.value)}
                                        className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-white dark:bg-dark-borde text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button"
                                    >
                                        <option value="" disabled>Selecciona un autor</option>
                                        {listaAutores.map(aut => (
                                            <option key={aut.idAutor} value={aut.idAutor}>{aut.nombreAutor}</option>
                                        ))}
                                    </select>
                                )}
                            </div>

                            {/* Año y Páginas */}
                            <div className="grid grid-cols-2 gap-4">
                                <div className="flex flex-col gap-1">
				    <label className="font-medium text-xs">Año de publicación *</label>
				    <input 
					type="text" 
					inputMode="numeric" 
					pattern="[0-9]*"
					required 
					value={ano} 
					onChange={(e) => setAno(e.target.value.replace(/[^0-9]/g, ''))}
					className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
				    />
				</div>

				<div className="flex flex-col gap-1">
				    <label className="font-medium text-xs">Número de páginas *</label>
				    <input 
					type="text" 
					inputMode="numeric" 
					pattern="[0-9]*"
					required 
					value={paginas} 
					onChange={(e) => setPaginas(e.target.value.replace(/[^0-9]/g, ''))}
					className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
				    />
				</div>

                            </div>

                            {/* DROPDOWN: Género */}
                            <div className="flex flex-col gap-1">
                                <div className="flex justify-between items-center h-4">
                                    <label className="font-medium text-xs">Género *</label>
                                    <button 
                                        type="button" 
                                        onClick={() => setCreandoGenero(!creandoGenero)}
                                        className="text-xs text-[#d4a373] hover:underline font-semibold focus:outline-none"
                                    >
                                        {creandoGenero ? 'Seleccionar existente' : '+ Nuevo'}
                                    </button>
                                </div>
                                {creandoGenero ? (
                                    <div className="flex gap-2">
                                        <input 
                                            type="text" 
                                            placeholder="Nombre del nuevo género"
                                            value={nuevoGeneroNombre} 
                                            onChange={(e) => setNuevoGeneroNombre(e.target.value)}
                                            className="flex-grow p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
                                        />
                                        <button 
                                            type="button"
                                            onClick={handleCrearGenero}
                                            className="bg-gold-button text-white px-4 py-2.5 rounded-lg hover:opacity-90 transition-opacity font-medium text-xs"
                                        >
                                            Guardar
                                        </button>
                                    </div>
                                ) : (
                                    <select 
                                        required value={idGenero} onChange={(e) => setIdGenero(e.target.value)}
                                        className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-white dark:bg-dark-borde text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button"
                                    >
                                        <option value="" disabled>Selecciona un género</option>
                                        {listaGeneros.map(gen => (
                                            <option key={gen.idGenero} value={gen.idGenero}>{gen.nombreGenero}</option>
                                        ))}
                                    </select>
                                )}
                            </div>

                            {/* DROPDOWN: Editorial */}
                            <div className="flex flex-col gap-1">
                                <div className="flex justify-between items-center h-4">
                                    <label className="font-medium text-xs">Editorial *</label>
                                    <button 
                                        type="button" 
                                        onClick={() => setCreandoEditorial(!creandoEditorial)}
                                        className="text-xs text-[#d4a373] hover:underline font-semibold focus:outline-none"
                                    >
                                        {creandoEditorial ? 'Seleccionar existente' : '+ Nueva'}
                                    </button>
                                </div>
                                {creandoEditorial ? (
                                    <div className="flex gap-2">
                                        <input 
                                            type="text" 
                                            placeholder="Nombre de la editorial"
                                            value={nuevoEditorialNombre} 
                                            onChange={(e) => setNuevoEditorialNombre(e.target.value)}
                                            className="flex-grow p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
                                        />
                                        <button 
                                            type="button"
                                            onClick={handleCrearEditorial}
                                            className="bg-gold-button text-white px-4 py-2.5 rounded-lg hover:opacity-90 transition-opacity font-medium text-xs"
                                        >
                                            Guardar
                                        </button>
                                    </div>
                                ) : (
                                    <select 
                                        required value={idEditorial} onChange={(e) => setIdEditorial(e.target.value)}
                                        className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-white dark:bg-dark-borde text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button"
                                    >
                                        <option value="" disabled>Selecciona una editorial</option>
                                        {listaEditoriales.map(ed => (
                                            <option key={ed.idEditorial} value={ed.idEditorial}>{ed.nombreEditorial}</option>
                                        ))}
                                    </select>
                                )}
                            </div>

                            {/* ISBN */}
			    <div className="flex flex-col gap-1">
				<label className="font-medium text-xs">ISBN *</label>
				<input 
				    type="text" 
				    inputMode="numeric" 
				    required 
				    value={isbn} 
				    onChange={(e) => {

					// Extrae solo los números 
					const numerosPuros = e.target.value.replace(/[^0-9]/g, "");
					
					// Limita a un máximo de 13 números 
					if (numerosPuros.length <= 13) {
					    let resultadoFormateado = "";
					    
					    // Construye el formato 978-0-00-000000-0 dinámicamente

					    // Primer bloque: primeros 3 dígitos (Ej: 978)
					    if (numerosPuros.length > 0) {
						resultadoFormateado += numerosPuros.substring(0, 3);
					    }

					    // Segundo bloque: 1 dígito (Ej: 978-3)
					    if (numerosPuros.length > 3) {
						resultadoFormateado += "-" + numerosPuros.substring(3, 4);
					    }

					    // Tercer bloque: 2 dígitos (Ej: 978-3-16)
					    if (numerosPuros.length > 4) {
						resultadoFormateado += "-" + numerosPuros.substring(4, 6);
					    }

					    // Cuarto bloque: 6 dígitos (Ej: 978-3-16-148410)
					    if (numerosPuros.length > 6) {
						resultadoFormateado += "-" + numerosPuros.substring(6, 12);
					    }

					    // Quinto bloque: último dígito (Ej: 978-3-16-148410-0)
					    if (numerosPuros.length > 12) {
						resultadoFormateado += "-" + numerosPuros.substring(12, 13);
					    }
					    
					    setIsbn(resultadoFormateado);
					}
				    }}
				    placeholder="000-0-00-000000-0"
				    className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button"
				/>
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
				    disabled={formularioInvalido}
				    className={`w-full p-3 rounded-lg font-medium transition-colors
                                       ${ formularioInvalido
                                           ? 'bg-gray-300 text-gray-500 cursor-not-allowed dark:bg-zinc-700 dark:text-zinc-400'
                                           : 'bg-gold-button text-white hover:bg-gold-button-hover cursor-pointer'
                                     }`}
                                 >
                                    {cargando ? 'Registrando...' : 'Añadir libro +'}
                                </button>
                                <button 
                                    type="button" onClick={() => navigate('/biblioteca')}
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
