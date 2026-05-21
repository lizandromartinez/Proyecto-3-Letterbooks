import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { obtenerPerfilPublico } from "../api/Perfil";
import Navbar from "../componentes/navegacion/navbar/Navbar";
import BannerPerfil from "../componentes/perfil/BannerPerfil";
import InfoPerfil from "../componentes/perfil/InfoPerfil";
import FavoritosPerfil from "../componentes/perfil/FavoritosPerfil";
import ActividadPerfil from "../componentes/perfil/ActividadPerfil";

/**
 * Componente para visualizar el perfil público de cualquier usuario.
 * <p>
 * No requiere autenticación. Para acceder se usa el endpoint: /usuario/<nombreUsuario>
 * </p>
 *
 * @component
 * @returns {JSX.Element} vista completa del perfil público del usuario.
 */
function PerfilPublico() {
    const { nombreUsuario } = useParams();
    const [perfil, setPerfil] = useState(null);
    const [error, setError] = useState(null);
    const [tabActiva, setTabActiva] = useState("likeadas");
    const estaAutenticado = !!localStorage.getItem("token");

    useEffect(() => {
        /**
         * Solicita al backend el perfil del usuario indicado en la URL.
         */
        const obtenerPerfil = async () => {
            try {
                setPerfil(await obtenerPerfilPublico(nombreUsuario));
            } catch {
                setError("No se encontró el perfil");
            }
        };
        obtenerPerfil();
    }, [nombreUsuario]);

    if (error) return (
        <div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo flex items-center justify-center">
            <p className="text-gray-500 dark:text-gray-400 font-inter">{error}</p>
        </div>
    );
    if (!perfil) return (
        <div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo flex items-center justify-center">
            <p className="text-gray-500 dark:text-gray-400 font-inter">Cargando perfil...</p>
        </div>
    );

    return (
        <div className="min-h-screen bg-crema-fondo dark:bg-dark-fondo transition-colors duration-500">
            <Navbar estaAutenticado={estaAutenticado} />
            <div className="py-8 px-4">
                <div className="max-w-4xl mx-auto bg-white dark:bg-dark-borde rounded-2xl shadow-md overflow-hidden border border-gray-100 dark:border-white/5">
                    <BannerPerfil perfil={perfil} />
                    <div className="px-6 md:px-10">
                        <div className="py-6 flex flex-col gap-8">
                            <InfoPerfil perfil={perfil} />
                            <FavoritosPerfil perfil={perfil} />
                            <ActividadPerfil
                                perfil={perfil}
                                tabActiva={tabActiva}
                                setTabActiva={setTabActiva}
                                tituloSeccion="Actividad"
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PerfilPublico;
