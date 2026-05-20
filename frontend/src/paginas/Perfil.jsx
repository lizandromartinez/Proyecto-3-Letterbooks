import { useEffect, useState } from "react";
import axios from "axios";

/**
 * Extrae el ID de usuario desde el token JWT.
 *
 * @param {string} token token JWT almacenado en localStorage
 * @returns {number|null} id del usuario o null si no se puede decodificar
 */
function obtenerIdDesdeToken(token) {
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.id_usuario || null;
    } catch {
        return null;
    }
}

/**
 * Componente de perfil del usuario.
 * <p>
 * Encargado de obtener y mostrar la información del perfil
 * del usuario autenticado mediante JWT.
 * </p>
 *
 * @component
 * @returns {JSX.Element} Vista del perfil del usuario
 */
function Perfil() {

    /** Datos del perfil obtenidos desde el backend */
    const [perfil, setPerfil] = useState(null);

    /** Estado de error en la carga del perfil */
    const [error, setError] = useState(null);

    useEffect(() => {

	/**
         * Obtiene el perfil del usuario autenticado desde la API.
         */
        const obtenerPerfil = async () => {
            const token = localStorage.getItem("token");
            if (!token) {
                setError("No hay sesión activa");
                return;
            }
            const idUsuario = obtenerIdDesdeToken(token);
            if (!idUsuario) {
                setError("No se pudo obtener el ID del usuario desde el token");
                return;
            }
            try {
                const respuesta = await axios.get(
                    `http://localhost:8080/api/usuarios/${idUsuario}/perfil`,
                    { headers: { Authorization: `Bearer ${token}` } }
                );
                setPerfil(respuesta.data);
            } catch (err) {
                console.error("Error al obtener perfil:", err);
                setError("Error al cargar el perfil");
            }
        };
        obtenerPerfil();
    }, []);

    if (error) return <p>{error}</p>;
    if (!perfil) return <p>Cargando perfil...</p>;

    return (
        <div>
	    {perfil.banner && (
		<img
                    src={perfil.banner}
                    alt="Banner"
                    style={{ width: "100%", height: "200px", objectFit: "cover" }}
		/>
            )}
            {perfil.avatar && (
                <img src={perfil.avatar} alt="Avatar" width="150" />
            )}	    
            <h1>{perfil.nombreUsuario || "Sin nombre de usuario"}</h1>
            <p>{perfil.biografia || "Sin biografía aún"}</p>
            <h3>Libro favorito:</h3>
            <p>{perfil.libroFavorito || "No definido"}</p>
            <h3>Autor favorito:</h3>
            <p>{perfil.autorFavorito || "No definido"}</p>
            <h3>Género favorito:</h3>
            <p>{perfil.generoFavorito || "No definido"}</p>
        </div>
    );
}

export default Perfil;
