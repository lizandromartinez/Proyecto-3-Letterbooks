import React, { useState, useContext } from 'react';
import { ContextoSesion } from '../contexto/Sesion';
import { peticionLogin } from '../api/Usuarios';
import { validarNombreUsuario, validarContrasena } from '../utilidades/Validadores';

/**
 * Componente que renderiza el formulario de inicio de sesión.
 * Maneja el estado local de los campos y gestiona los errores de validación.
 */
const InicioDeSesion = () => {
    const { iniciarSesion } = useContext(ContextoSesion);
    const [credenciales, setCredenciales] = useState({ nombreUsuario: '', contrasena: '' });
    const [error, setError] = useState('');

    /* Actualiza el estado cuando el usuario escribe en los inputs. */
    const manejarCambio = (e) => {
        setCredenciales({ ...credenciales, [e.target.name]: e.target.value });
    };

    /* Gestiona el envío del formulario. */
    const manejarEnvio = async (e) => {
        e.preventDefault();
        setError('');

        // Validaciones locales antes de ir al servidor
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
            alert("¡Bienvenido a LetterBooks!");
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="contenedor-login">
            <form onSubmit={manejarEnvio}>
                <h2>Iniciar Sesión</h2>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                <input
                    type="text"
                    name="nombreUsuario"
                    placeholder="Nombre de usuario"
                    onChange={manejarCambio}
                    required
                />
                <input
                    type="password"
                    name="contrasena"
                    placeholder="Contraseña"
                    onChange={manejarCambio}
                    required
                />
                <button type="submit">Entrar</button>
            </form>
        </div>
    );
};

export default InicioDeSesion;
