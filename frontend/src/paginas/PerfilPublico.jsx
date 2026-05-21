import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import avatarDefecto from "../estilos/img/defecto/avatar.jpg";
import bannerDefecto from "../estilos/img/defecto/banner.png";

/**
 * Resuelve la URL correcta de una imagen según su origen.
 */
function resolverUrlImagen(ruta, imagenDefecto) {
    if (!ruta) return imagenDefecto;
    if (ruta.startsWith("/estilos/")) return imagenDefecto;
    return `http://localhost:8080${ruta}`;
}

/**
 * Componente para ver el perfil público de cualquier usuario.
 */
function PerfilPublico() {
    const { nombreUsuario } = useParams();
    const [perfil, setPerfil] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const obtenerPerfil = async () => {
            try {
                const respuesta = await axios.get(
                    `http://localhost:8080/api/usuarios/perfil/${nombreUsuario}`
                );
                setPerfil(respuesta.data);
            } catch (err) {
                setError("No se encontró el perfil");
            }
        };
        obtenerPerfil();
    }, [nombreUsuario]);

    if (error) return <p>{error}</p>;
    if (!perfil) return <p>Cargando perfil...</p>;

    return (
        <div style={{ maxWidth: "900px", margin: "0 auto", padding: "20px", fontFamily: "Arial" }}>

            {/* Banner */}
            <img
                src={resolverUrlImagen(perfil.banner, bannerDefecto)}
                alt="Banner"
                style={{ width: "100%", height: "220px", objectFit: "cover", borderRadius: "10px" }}
            />

            {/* Avatar */}
            <img
                src={resolverUrlImagen(perfil.avatar, avatarDefecto)}
                alt="Avatar"
                style={{
                    width: "150px", height: "150px", borderRadius: "50%",
                    marginTop: "-60px", border: "4px solid white", backgroundColor: "white"
                }}
            />

            {/* Nombre */}
            <h1 style={{ marginTop: "10px" }}>
                {perfil.nombreUsuario || "Sin nombre de usuario"}
            </h1>

            <div style={{ marginTop: "20px" }}>

                {/* Biografía */}
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Biografía</h3>
                    <p>{perfil.biografia || "Sin biografía aún"}</p>
                </div>

                {/* Favoritos */}
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Libro favorito</h3>
                    <p>{perfil.libroFavorito || "No definido"}</p>
                </div>
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Autor favorito</h3>
                    <p>{perfil.autorFavorito || "No definido"}</p>
                </div>
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Género favorito</h3>
                    <p>{perfil.generoFavorito || "No definido"}</p>
                </div>

                {/* Reseñas likeadas */}
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Reseñas que le gustaron</h3>
                    {perfil.resenasLikeadas && perfil.resenasLikeadas.length > 0 ? (
                        perfil.resenasLikeadas.map(r => (
                            <div key={r.idResena} style={{
                                padding: "10px", borderBottom: "1px solid #eee", marginBottom: "10px"
                            }}>
                                <strong>{r.tituloLibro}</strong>
                                <p style={{ fontSize: "12px", color: "gray" }}>
                                    por {r.autorResena} · {r.fechaLike}
                                </p>
                                <p>{r.textoResena}</p>
                            </div>
                        ))
                    ) : <p>Ninguna aún</p>}
                </div>

                {/* Reseñas calificadas */}
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Reseñas que ha calificado</h3>
                    {perfil.resenasCalificadas && perfil.resenasCalificadas.length > 0 ? (
                        perfil.resenasCalificadas.map(r => (
                            <div key={r.idResena} style={{
                                padding: "10px", borderBottom: "1px solid #eee", marginBottom: "10px"
                            }}>
                                <strong>{r.tituloLibro}</strong>
                                <p style={{ fontSize: "12px", color: "gray" }}>
                                    por {r.autorResena} · {r.fechaCalificacion}
                                </p>
                                <p>{r.textoResena}</p>
                                <p style={{ fontWeight: "bold" }}>Calificación: {r.calificacion}/10</p>
                            </div>
                        ))
                    ) : <p>Ninguna aún</p>}
                </div>

                {/* Libros calificados */}
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Libros que ha calificado</h3>
                    {perfil.librosCalificados && perfil.librosCalificados.length > 0 ? (
                        perfil.librosCalificados.map(l => (
                            <div key={l.idLibro} style={{
                                display: "flex", alignItems: "center", gap: "15px",
                                padding: "10px", borderBottom: "1px solid #eee", marginBottom: "10px"
                            }}>
                                {l.imagen && (
                                    <img
                                        src={resolverUrlImagen(l.imagen, null)}
                                        alt={l.titulo}
                                        style={{ width: "60px", height: "90px", objectFit: "cover", borderRadius: "4px" }}
                                    />
                                )}
                                <div>
                                    <strong>{l.titulo}</strong>
                                    <p style={{ fontSize: "12px", color: "gray" }}>{l.autor}</p>
                                    <p style={{ fontWeight: "bold" }}>Calificación: {l.calificacion}/10</p>
                                </div>
                            </div>
                        ))
                    ) : <p>Ninguno aún</p>}
                </div>

                {/* Comentarios likeados */}
                <div style={{ padding: "15px", border: "1px solid #ddd", borderRadius: "8px", marginBottom: "15px" }}>
                    <h3>Comentarios que le gustaron</h3>
                    {perfil.comentariosLikeados && perfil.comentariosLikeados.length > 0 ? (
                        perfil.comentariosLikeados.map(c => (
                            <div key={c.idComentario} style={{
                                padding: "10px", borderBottom: "1px solid #eee", marginBottom: "10px"
                            }}>
                                <p style={{ fontSize: "12px", color: "gray" }}>
                                    en <strong>{c.tituloLibro}</strong> · por {c.autorComentario} · {c.fechaLike}
                                </p>
                                <p>{c.texto}</p>
                            </div>
                        ))
                    ) : <p>Ninguno aún</p>}
                </div>

            </div>
        </div>
    );
}

export default PerfilPublico;
