import React, { useEffect, useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import EnlaceRuta from '../navegacion/EnlaceRuta';
import { ContextoSesion } from '../../contexto/Sesion';
import { obtenerUsuarioDelToken } from '../../utilidades/DecodificadorToken';
import Cita from './Cita';
import like from '../../estilos/img/iconos/like.png'; 
import comentarioIcon from '../../estilos/img/iconos/comentario.png';
import avatarDefecto from '../../estilos/img/defecto/avatar.jpg';
import { obtenerResenasRecientes } from '../../api/Resenas';

const obtenerUrlImagen = (ruta) => {
    if (!ruta) return 'https://via.placeholder.com/80x120?text=Sin+Portada';
    if (ruta.startsWith('http')) return ruta;
    return `http://localhost:8080${ruta}`;
};

const formatearFecha = (fechaIso) => {
    if (!fechaIso) return '';
    const fecha = new Date(fechaIso);
    if (Number.isNaN(fecha.getTime())) return fechaIso;
    return fecha.toLocaleDateString('es-ES', { day: 'numeric', month: 'long' });
};

/**
 * COMPONENTE: ResenaCard
 * Representación visual de una reseña individual.
 */
const ResenaCard = ({ resena }) => {

    const { token } = useContext(ContextoSesion);
    const usuarioActual = obtenerUsuarioDelToken(token);

    // Determina la ruta según si es el perfil propio o ajeno
    const rutaPerfil = usuarioActual === resena.usuario
        ? "/perfil"
        : `/usuario/${resena.usuario}`;
    
    return (
        <div className="bg-white dark:bg-dark-borde p-8 rounded-3xl border border-gray-100 dark:border-white/10 shadow-sm hover:shadow-md transition-shadow duration-300 max-w-3xl mx-auto mb-10">
	    <div className="flex justify-between items-start mb-6">
		<div className="flex items-center gap-4">
		    <Link to={rutaPerfil}>
			<img
			    src={resena.userImg
				? `http://localhost:8080${resena.userImg}`
				: avatarDefecto}
			    alt={resena.usuario}
			    className="w-12 h-12 rounded-full border-2 border-gold-button/20 object-cover hover:border-gold-button hover:scale-105 transition-all duration-200 cursor-pointer"
			/>
		    </Link>
		    <div className="text-left">
			<Link
			    to={rutaPerfil}
			    className="font-bold text-navy-letter dark:text-white leading-tight hover:text-gold-button dark:hover:text-gold-button transition-colors duration-200 cursor-pointer"
			>
			    {resena.usuario}
			</Link>
			<p className="text-gray-400 text-sm">@{resena.usuario}</p>
		    </div>
		</div>
		<span className="text-gray-400 text-xs font-inter">{resena.fecha}</span>
	    </div>

            <EnlaceRuta 
                to={`/libro/${resena.idLibro}`}
                state={{ libroData: {
                    idLibro: resena.idLibro,
                    titulo: resena.libro,
                    imagen: resena.libroImg,
                    nombreAutor: resena.autor,
                } }}
                className="flex gap-4 mb-6 p-4 bg-[#dedfe0] dark:bg-white/5 rounded-2xl border border-gold-button/5 hover:bg-[#c7c7c7] transition-all duration-300"
            >
                <img 
                    src={obtenerUrlImagen(resena.libroImg)} 
                    alt={resena.libro} 
                    className="w-14 h-20 object-cover rounded-lg shadow-sm" 
                />
                <div className="text-left flex flex-col justify-center">
                    <h5 className="font-cormorant font-bold text-lg dark:text-gray-100 leading-tight">
                        {resena.libro}
                    </h5>
                    <p className="text-gray-500 text-xs mb-2">{resena.autor}</p>
                    <div className="flex text-gold-button text-xs">
                        {"★".repeat(resena.estrellas)}{"☆".repeat(5 - resena.estrellas)}
                    </div>
                </div>
            </EnlaceRuta>

            <p className="text-black dark:text-white text-left mb-6 font-inter leading-relaxed">
                {resena.comentario}
            </p>

            <div className="space-y-2 mb-6">
                {resena.citas && resena.citas.map((item, index) => (
                    <Cita 
                        key={index} 
                        texto={item.texto} 
                        pagina={item.pagina} 
                    />
                ))}
            </div>

            <div className="flex items-center w-full gap-4 border-t border-gray-50 dark:border-white/5">
                <button type="button" className="flex items-center gap-1.5 text-gray-400 hover:text-red-500 transition-colors group cursor-pointer">
                    <img
                        src={like} 
                        alt="Icono de Like" 
                        className="h-5 w-auto dark:invert dark:brightness-200 transition-all group-hover:scale-110"
                    />
                    <span className="text-xs font-bold">{resena.likes}</span>
                </button>
                
                <button type="button" className="flex items-center gap-1.5 text-gray-400 hover:text-navy-letter dark:hover:text-white transition-colors group cursor-pointer">
                    <img
                        src={comentarioIcon} 
                        alt="Icono de Comentario" 
                        className="h-5 w-auto dark:invert dark:brightness-200 transition-all group-hover:scale-110"
                    />
                    <span className="text-xs font-bold">{resena.comments}</span>
                </button>

                <div className="ml-auto flex items-center gap-2">
                    <span className="text-xs text-gray-400 font-inter font-medium hidden sm:inline">
                        Calificación de la reseña:
                    </span>
                    <div className="flex text-gold-button text-xs tracking-wider">
                        {"★".repeat(resena.calificacionResena)}{"☆".repeat(5 - resena.calificacionResena)}
                    </div>
                </div>
            </div>
        </div>
    );
};

/**
 * COMPONENTE PRINCIPAL: ReseñasRecientes
 * Gestiona el conjunto de datos y el layout de la sección.
 */
const ResenasRecientes = () => {
    const [resenas, setResenas] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const cargarResenas = async () => {
            try {
                setCargando(true);
                setError(null);
                const data = await obtenerResenasRecientes(5);
                const mapeadas = data.map((resena) => ({
                    id: resena.idResena,
                    idLibro: resena.idLibro,
                    usuario: resena.nombreUsuario,
                    arroba: resena.nombreUsuario,
                    fecha: formatearFecha(resena.fechaPublicacion),
                    userImg: resena.avatarUsuario,
                    libro: resena.tituloLibro,
                    autor: resena.nombreAutor,
                    libroImg: resena.imagenLibro,
                    estrellas: resena.calificacionLibro ?? 0,
                    calificacionResena: resena.calificacionResena ?? 0,
                    comentario: resena.textoResena,
                    citas: resena.citas ?? [],
                    likes: resena.likes ?? 0,
                    comments: resena.totalComentarios ?? 0,
                }));
                setResenas(mapeadas);
            } catch (err) {
                console.error('Error al cargar reseñas recientes:', err);
                setError('No se pudieron cargar las reseñas recientes.');
            } finally {
                setCargando(false);
            }
        };

        cargarResenas();
    }, []);

    return (
        <section className="py-20 px-10 bg-[#FDF8F3] dark:bg-dark-fondo transition-colors duration-300">
            <div className="max-w-4xl mx-auto text-left mb-10">
                <h2 className="font-cormorant text-3xl font-bold text-navy-letter dark:text-gray-100">
                    Reseñas recientes
                </h2>                
            </div>

            {cargando && (
                <p className="text-center text-gray-500 dark:text-gray-400 font-inter">Cargando reseñas...</p>
            )}

            {error && (
                <p className="text-center text-red-500 font-inter">{error}</p>
            )}

            {!cargando && !error && resenas.length === 0 && (
                <p className="text-center text-gray-500 dark:text-gray-400 font-inter">Aún no hay reseñas publicadas.</p>
            )}
            
            {resenas.map(resena => (
                <ResenaCard key={resena.id} resena={resena} />
            ))}
        </section>
    );
};

export default ResenasRecientes;
