import { useEffect, useState } from "react";
import axios from "axios";

/**
 * Extrae el ID de usuario desde el token JWT.
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
 */
function Perfil() {
    const [perfil, setPerfil] = useState(null);
    const [error, setError] = useState(null);
    const [editando, setEditando] = useState(false);
    const [esPropietario, setEsPropietario] = useState(false);
    const [token, setToken] = useState(null);
    const [idUsuario, setIdUsuario] = useState(null);

    // Campos editables
    const [biografia, setBiografia] = useState("");
    const [avatar, setAvatar] = useState("");
    const [banner, setBanner] = useState("");
    const [idAutorFavorito, setIdAutorFavorito] = useState("");
    const [idGeneroFavorito, setIdGeneroFavorito] = useState("");
    const [idLibroFavorito, setIdLibroFavorito] = useState("");

    useEffect(() => {
        const obtenerPerfil = async () => {
            const tokenGuardado = localStorage.getItem("token");
            if (!tokenGuardado) {
                setError("No hay sesión activa");
                return;
            }

            const id = obtenerIdDesdeToken(tokenGuardado);
            if (!id) {
                setError("No se pudo obtener el ID del usuario desde el token");
                return;
            }

            setToken(tokenGuardado);
            setIdUsuario(id);
	    // si se llega a este punto con su token, es su perfil
            setEsPropietario(true);

            try {
                const respuesta = await axios.get(
                    `http://localhost:8080/api/usuarios/${id}/perfil`,
                    { headers: { Authorization: `Bearer ${tokenGuardado}` } }
                );
                setPerfil(respuesta.data);

                // Precargamos los campos con los valores actuales
                setBiografia(respuesta.data.biografia || "");
                setAvatar(respuesta.data.avatar || "");
                setBanner(respuesta.data.banner || "");
            } catch (err) {
                console.error("Error al obtener perfil:", err);
                setError("Error al cargar el perfil");
            }
        };

        obtenerPerfil();
    }, []);

    /**
     * Envía los datos actualizados al backend.
     */
    const guardarCambios = async () => {
        try {
            const datos = {
                biografia,
                avatar,
                banner,
                idAutorFavorito: idAutorFavorito ? parseInt(idAutorFavorito) : null,
                idGeneroFavorito: idGeneroFavorito ? parseInt(idGeneroFavorito) : null,
                idLibroFavorito: idLibroFavorito ? parseInt(idLibroFavorito) : null,
            };

            const respuesta = await axios.put(
                `http://localhost:8080/api/usuarios/${idUsuario}/perfil`,
                datos,
                { headers: { Authorization: `Bearer ${token}` } }
            );

            setPerfil(respuesta.data);
            setEditando(false);
        } catch (err) {
            console.error("Error al actualizar perfil:", err);
            setError("Error al guardar los cambios");
        }
    };

    if (error) return <p>{error}</p>;
    if (!perfil) return <p>Cargando perfil...</p>;

    return (
        <div>
            {perfil.banner && (
                <img src={perfil.banner} alt="Banner"
                    style={{ width: "100%", height: "200px", objectFit: "cover" }} />
            )}
            {perfil.avatar && (
                <img src={perfil.avatar} alt="Avatar" width="150" />
            )}
            <h1>{perfil.nombreUsuario || "Sin nombre de usuario"}</h1>

            {/* Solo muestra el botón de editar si es el propietario y no está editando */}
            {esPropietario && !editando && (
                <button onClick={() => setEditando(true)}>Editar perfil</button>
            )}

            {editando ? (
                <div>
                    <h3>Editar perfil</h3>

                    <label>Biografía:</label>
                    <textarea value={biografia}
                        onChange={e => setBiografia(e.target.value)} />

                    <label>URL Avatar:</label>
                    <input value={avatar}
                        onChange={e => setAvatar(e.target.value)} />

                    <label>URL Banner:</label>
                    <input value={banner}
                        onChange={e => setBanner(e.target.value)} />

                    <label>ID Autor favorito:</label>
                    <input value={idAutorFavorito}
                        onChange={e => setIdAutorFavorito(e.target.value)} />

                    <label>ID Género favorito:</label>
                    <input value={idGeneroFavorito}
                        onChange={e => setIdGeneroFavorito(e.target.value)} />

                    <label>ID Libro favorito:</label>
                    <input value={idLibroFavorito}
                        onChange={e => setIdLibroFavorito(e.target.value)} />

                    <button onClick={guardarCambios}>Guardar</button>
                    <button onClick={() => setEditando(false)}>Cancelar</button>
                </div>
            ) : (
                <div>
                    <p>{perfil.biografia || "Sin biografía aún"}</p>
                    <h3>Libro favorito:</h3>
                    <p>{perfil.libroFavorito || "No definido"}</p>
                    <h3>Autor favorito:</h3>
                    <p>{perfil.autorFavorito || "No definido"}</p>
                    <h3>Género favorito:</h3>
                    <p>{perfil.generoFavorito || "No definido"}</p>
                </div>
            )}
        </div>
    );
}

export default Perfil;
