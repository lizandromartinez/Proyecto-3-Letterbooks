import React, { useContext, useState, useEffect } from 'react';
import { useLocation, Navigate, useParams, useNavigate } from 'react-router-dom'; 
import { ContextoSesion } from '../contexto/Sesion';

import Navbar from '../componentes/navegacion/navbar/Navbar';
import Footer from '../componentes/navegacion/footer/Footer';
import EnlaceRuta from '../componentes/navegacion/EnlaceRuta';
import ListaResenas from '../componentes/resenas/ListaResenas';
import { obtenerLibroPorId } from '../api/Libros';
import { obtenerUsuarioDelToken, obtenerRolDelToken, obtenerIdUsuarioDelToken } from '../utilidades/DecodificadorToken';

const coincideLibro = (datos, idRuta) =>
    datos && String(datos.idLibro) === String(idRuta);

/**
 * DetalleLibro - Vista detallada de un libro.
 */
const DetalleLibro = () => {
    const { token } = useContext(ContextoSesion);
    const { id } = useParams();
    const location = useLocation();
    const navigate = useNavigate();

    const rolUsuarioActual = obtenerRolDelToken(token);
    const idUsuarioActual = obtenerIdUsuarioDelToken(token);

    const datosNavegacion = location.state?.libroData;
    const tieneVistaPrevia = coincideLibro(datosNavegacion, id);

    const [libro, setLibro] = useState(tieneVistaPrevia ? datosNavegacion : null);
    const [cargando, setCargando] = useState(!tieneVistaPrevia);
    const [miCalificacion, setMiCalificacion] = useState(null);

    const puedeEditar = libro && (rolUsuarioActual === 'admin' || !libro.idUsuarioCreador || libro.idUsuarioCreador === idUsuarioActual);

    useEffect(() => {
        if (!token || !id) return;

        let cancelado = false;

        const cargarDetalles = async () => {
            if (!tieneVistaPrevia) {
                setCargando(true);
            }

            try {
                const data = await obtenerLibroPorId(id, token);
                if (!cancelado) {
                    setLibro(data);
                }
            } catch (err) {
                console.error('Error al obtener los detalles del libro:', err);
                if (!cancelado && tieneVistaPrevia) {
                    setLibro(datosNavegacion);
                }
            } finally {
                if (!cancelado) {
                    setCargando(false);
                }
            }
        };

        cargarDetalles();

        return () => {
            cancelado = true;
        };
    }, [id, token, tieneVistaPrevia, datosNavegacion]);

    const refrescarDetallesLibro = async () => {
        if (!token || !id) return;
        try {
            const data = await obtenerLibroPorId(id, token);
            setLibro(data);
        } catch (err) {
            console.error('Error al actualizar los detalles del libro:', err);
        }
    };

    const actualizarMiCalificacion = (resenas) => {
        const usuarioActual = obtenerUsuarioDelToken(token);
        const miResena = resenas.find((r) => r.usuario?.nombreUsuario === usuarioActual);
        setMiCalificacion(miResena?.calificacionLibro ?? null);
    };

    if (!token) {
        return (
            <Navigate
                to="/login"
                replace
                state={{
                    from: location.pathname,
                    libroData: location.state?.libroData,
                }}
            />
        );
    }

    const obtenerUrlImagen = (ruta) => {
        if (!ruta) return 'https://via.placeholder.com/300x450?text=Sin+Portada';
        if (ruta.startsWith('http')) return ruta;
        return `http://localhost:8080${ruta}`;
    };

    if (cargando && !libro) {
        return (
            <div className="bg-crema-fondo min-h-screen dark:bg-dark-fondo flex flex-col font-inter text-navy-letter dark:text-gray-100">
                <Navbar estaAutenticado={true} />
                <main className="flex-grow flex items-center justify-center min-h-[70vh]">
                    <p className="text-gray-500 font-bold">Cargando detalles del libro...</p>
                </main>
            </div>
        );
    }

    if (!libro) {
        return (
            <div className="bg-crema-fondo min-h-screen dark:bg-dark-fondo flex flex-col font-inter text-navy-letter dark:text-gray-100">
                <Navbar estaAutenticado={true} />
                <main className="flex-grow flex flex-col items-center justify-center min-h-[70vh] text-gray-500">
                    <p className="mb-4">No se pudo cargar el libro solicitado.</p>
                    <EnlaceRuta to="/biblioteca" className="text-[#d4a373] underline font-bold">
                        Volver a la Biblioteca
                    </EnlaceRuta>
                </main>
                <Footer estaAutenticado={true} />
            </div>
        );
    }

    const autorNombre = libro.nombreAutor || libro.autor?.nombreAutor || 'Autor desconocido';
    const editorialNombre = libro.nombreEditorial || libro.editorial?.nombreEditorial || 'N/A';
    const generoNombre = libro.nombreGenero || libro.genero?.nombreGenero || 'Literatura';
    const promedioCalif = libro.promedioCalificacion != null ? Number(libro.promedioCalificacion).toFixed(1) : '0.0';

    return (
        <div className="bg-crema-fondo min-h-screen dark:bg-dark-fondo transition-colors duration-500 flex flex-col font-inter text-navy-letter dark:text-gray-100">
            <Navbar estaAutenticado={true} />

            <main className="flex-grow py-12 px-4 sm:px-10 max-w-7xl mx-auto w-full grid grid-cols-1 lg:grid-cols-3 gap-8 items-start">
                
                <div className="lg:col-span-2 bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-2xl p-6 sm:p-8 flex flex-col sm:flex-row gap-8 shadow-xs text-left">
                    
                    {/* Contenedor adaptativo de la Portada */}
                    <div className="w-full sm:w-64 md:w-72 shrink-0 rounded-xl overflow-hidden shadow-md bg-gray-100 dark:bg-white/5 self-start">
                        <img src={obtenerUrlImagen(libro.imagen)} alt={libro.titulo} className="w-full h-full object-contain block" />
                    </div>

                    <div className="flex flex-col flex-grow">
                        <h1 className="font-inter text-2xl font-bold text-gray-900 dark:text-white leading-tight">
                            {libro.titulo}
                        </h1>
                        <p className="text-gray-400 text-sm font-medium mt-1">
                            {autorNombre}
                        </p>

                        <div className="flex items-center gap-1.5 mt-3 text-sm">
                            <div className="text-[#d4a373] flex gap-0.5 text-base">
                                <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
                            </div>
                            <span className="font-bold text-gray-800 dark:text-gray-200 ml-1">
                                {promedioCalif}
                            </span>
                            <span className="text-gray-400 text-xs font-medium">
                                · {libro.totalResenas || 0} reseñas
                            </span>
                        </div>

                        <div className="mt-4 self-start">
                            <span className="bg-orange-50 dark:bg-amber-900/20 text-[#d4a373] text-xs font-bold px-3 py-1 rounded-md border border-amber-900/5 dark:border-amber-700/20">
                                {generoNombre}
                            </span>
                        </div>

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
                                <span className="text-gray-800 dark:text-gray-200 font-bold line-clamp-1">{editorialNombre}</span>
                            </div>
                            <div className="bg-gray-50 dark:bg-white/5 border border-gray-100 dark:border-white/5 rounded-xl p-3 flex flex-col gap-0.5">
                                <span className="text-gray-400">ISBN:</span>
                                <span className="text-gray-800 dark:text-gray-200 font-bold">{libro.isbn || 'N/A'}</span>
                            </div>
                        </div>

                        <div className="mt-6 border-t border-gray-100 dark:border-white/5 pt-4">
                            <h3 className="font-bold text-sm text-gray-900 dark:text-white mb-2">Sinopsis</h3>
                            <p className="text-gray-500 dark:text-gray-300 text-xs leading-relaxed font-normal">
                                {libro.sinopsis || 'Sin sinopsis disponible en el catálogo.'}
                            </p>
                        </div>

                    </div>
                </div>

                <div className="flex flex-col gap-4">
                    <div className="w-full bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-2xl p-6 shadow-xs text-center flex flex-col items-center justify-center min-h-[180px]">
                        <h3 className="text-xs font-bold text-gray-400 uppercase tracking-wider mb-4 self-start">
                            Tu calificación
                        </h3>
                        <div className="w-16 h-16 rounded-full bg-orange-50/50 dark:bg-white/5 border border-amber-900/5 dark:border-white/5 flex items-center justify-center text-[#d4a373] text-xl font-bold mb-3">
                            {miCalificacion ?? '-'}
                        </div>
                        <p className="text-gray-400 text-xs font-medium">
                            {miCalificacion != null
                                ? 'tu calificación para este libro'
                                : 'no has calificado este libro'}
                        </p>
                    </div>

                    {puedeEditar && (
                        <div className="w-full bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-2xl p-6 shadow-xs flex flex-col gap-3">
                            <h3 className="text-xs font-bold text-gray-400 uppercase tracking-wider self-start mb-1">
                                Gestión del libro
                            </h3>
                            <button
                                onClick={() => navigate(`/editarLibro/${libro.idLibro}`)}
                                className="w-full py-2.5 px-4 bg-[#d4a373] hover:bg-[#c39262] text-white font-bold rounded-xl transition-all duration-300 shadow-sm text-xs font-inter flex items-center justify-center gap-2 cursor-pointer border-none"
                            >
                                Editar Libro
                            </button>
                        </div>
                    )}
                </div>

                <section className="lg:col-span-3 mt-4">
                    <ListaResenas
                        idLibro={Number(id)}
                        alCambiarResenas={refrescarDetallesLibro}
                        alResenasActualizadas={actualizarMiCalificacion}
                    />
                </section>

            </main>

            <Footer estaAutenticado={true} />
        </div>
    );
};

export default DetalleLibro;
