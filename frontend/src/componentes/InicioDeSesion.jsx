import React, { useState, useContext } from 'react';
import { ContextoSesion } from '../contexto/Sesion';
import { peticionLogin } from '../api/Usuarios';
import { validarNombreUsuario, validarContrasena } from '../utilidades/Validadores';
import '../estilos/InicioDeSesion.css';

/**
 * Componente que renderiza el formulario de inicio de sesión.
 */
const InicioDeSesion = () => {
    const { iniciarSesion } = useContext(ContextoSesion);
    const [credenciales, setCredenciales] = useState({ nombreUsuario: '', contrasena: '' });
    const [error, setError] = useState('');

    /**
     * Actualiza el estado cuando el usuario escribe en los inputs.
     * @param {Object} e Evento del input.
     */
    const manejarCambio = (e) => {
        setCredenciales({ ...credenciales, [e.target.name]: e.target.value });
    };

    /**
     * Gestiona el envío del formulario realizando validaciones previas.
     * @param {Object} e Evento de envío del formulario.
     */
    const manejarEnvio = async (e) => {
        e.preventDefault();
        setError('');

        if (!validarNombreUsuario(credenciales.nombreUsuario)) {
            setError("El nombre de usuario no puede estar vacío.");
            return;
        }
        if (!validarContrasena(credenciales.contrasena)) {
            setError("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        try {
            const respuesta = await peticionLogin(credenciales);
            iniciarSesion(respuesta.token);
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="login-page">
            <button className="login-back" onClick={() => window.location.reload()}>
                &larr; Volver al inicio
            </button>

            <div className="login-brand">
                <h1>LETTER <span>BOOKS</span></h1>
                <p>Tu comunidad literaria</p>
            </div>

            <div className="login-card">
                <div className="login-tabs">
                    <button className="login-tab active">Iniciar sesión</button>
                    {/* El botón de registro por ahora es solo visual, se conectará a su ruta luego */}
                    <button className="login-tab" onClick={() => alert("Ir a Registro")}>Registrarse</button>
                </div>

                {error && <p className="login-error">{error}</p>}

                <form className="login-form" onSubmit={manejarEnvio}>
                    <div className="login-field">
                        <label>Usuario</label>
                        <input
                            type="text"
                            name="nombreUsuario"
                            placeholder="lector001"
                            value={credenciales.nombreUsuario}
                            onChange={manejarCambio}
                        />
                    </div>

                    <div className="login-field">
                        <label>Contraseña</label>
                        <input
                            type="password"
                            name="contrasena"
                            placeholder="••••••••"
                            value={credenciales.contrasena}
                            onChange={manejarCambio}
                        />
                    </div>

                    <button type="submit" className="login-btn">
                        Iniciar sesión
                    </button>
                </form>
                
                <p className="login-hint">Demo: usa cualquier nombre de usuario</p>
            </div>

            <div className="login-features">
                <div className="login-feature">
                    <span>&#128214;</span>
                    <span>Descubre<br/>libros</span>
                </div>
                <div className="login-feature">
                    <span>&#128218;</span>
                    <span>Comparte<br/>reseñas</span>
                </div>
                <div className="login-feature">
                    <span>&#128101;</span>
                    <span>Conecta con<br/>lectores</span>
                </div>
            </div>
        </div>
    );
};

export default InicioDeSesion;
