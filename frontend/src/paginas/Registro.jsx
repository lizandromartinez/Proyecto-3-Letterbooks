import { useState } from 'react';
import { registrarUsuario } from '../api/Usuarios';
import '../estilos/Registro.css';

/**
 * Componente de registro e inicio de sesión de usuarios.
 * <p>
 * Este componente permite:
 * - Registrar nuevos usuarios en el sistema
 * - Alternar entre vista de inicio de sesión y registro
 * - Validar datos del formulario antes de enviarlos al backend
 * - Consumir la API de registro de usuarios
 * </p>
 *
 * @component
 * @returns {JSX.Element} Pantalla de autenticación (registro/inicio de sesión)
 */
function Registro() {

    /**
     * Estado que controla la pestaña activa de la interfaz.
     * Valores posibles: 'inicioSesion' o 'registro'
     */
    const [pestana, setPestana] = useState('registro');

    /**
     * Estado del formulario de registro.
     * Contiene los datos ingresados por el usuario.
     */
    const [datosFormulario, setDatosFormulario] = useState({
        nombreUsuario: '',
        correo: '',
        contrasena: '',
        nombreCompleto: '',
    });

    /**
     * Estado que indica si el formulario está en proceso de envío.
     */
    const [cargando, setCargando] = useState(false);

    /**
     * Maneja los cambios en los campos del formulario.
     *
     * @param {React.ChangeEvent<HTMLInputElement>} evento evento del input
     */
    const manejarCambio = (evento) => {
        setDatosFormulario({
            ...datosFormulario,
            [evento.target.name]: evento.target.value
        });
    };

    /**
     * Maneja el envío del formulario de registro.
     * <p>
     * Valida el correo, limpia los datos y envía la solicitud al backend.
     * Muestra mensajes de éxito o error según la respuesta.
     * </p>
     *
     * @param {React.FormEvent<HTMLFormElement>} evento evento de envío
     */
    const manejarEnvio = async (evento) => {
        evento.preventDefault();

        const correoValido = /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/.test(datosFormulario.correo);
        if (!correoValido) {
            alert('Ingresa un correo válido');
            return;
        }

        setCargando(true);

        try {
            const datosLimpios = {
                nombreUsuario: datosFormulario.nombreUsuario.trim(),
                correo: datosFormulario.correo.trim().toLowerCase(),
                contrasena: datosFormulario.contrasena.trim(),
                nombreCompleto: datosFormulario.nombreCompleto.trim()
            };

            const usuario = await registrarUsuario(datosLimpios);

            console.log(usuario);
            alert('Usuario registrado');

            setDatosFormulario({
                nombreUsuario: '',
                correo: '',
                contrasena: '',
                nombreCompleto: ''
            });

        } catch (error) {
            console.error(error);

            if (typeof error === 'object') {
                alert(Object.values(error).join('\n'));
            } else {
                alert(error);
            }

        } finally {
            setCargando(false);
        }
    };

    return (
        <div className="registro-page">

            {/* enlace de regreso */}
            <a href="/" className="registro-back">← Volver al inicio</a>

            {/* marca */}
            <div className="registro-brand">
                <h1>LETTER 📖 <span>BOOKS</span></h1>
                <p>Tu comunidad literaria</p>
            </div>

            {/* tarjeta */}
            <div className="registro-card">

                {/* pestañas */}
                <div className="registro-tabs">
                    <button
                        type="button"
                        className={`registro-tab ${pestana === 'inicioSesion' ? 'active' : ''}`}
                        onClick={() => setPestana('inicioSesion')}
                    >
                        Iniciar sesión
                    </button>

                    <button
                        type="button"
                        className={`registro-tab ${pestana === 'registro' ? 'active' : ''}`}
                        onClick={() => setPestana('registro')}
                    >
                        Registrarse
                    </button>
                </div>

                {/* formulario */}
                <form className="registro-form" onSubmit={manejarEnvio}>

                    <div className="registro-field">
                        <label htmlFor="nombreUsuario">Usuario</label>
                        <input
                            id="nombreUsuario"
                            type="text"
                            name="nombreUsuario"
                            placeholder="ana_lee"
                            value={datosFormulario.nombreUsuario}
                            onChange={manejarCambio}
                            required
                        />
                    </div>

                    <div className="registro-field">
                        <label htmlFor="contrasena">Contraseña</label>
                        <input
                            id="contrasena"
                            type="password"
                            name="contrasena"
                            placeholder="••••••••"
                            value={datosFormulario.contrasena}
                            onChange={manejarCambio}
                            required
                        />
                    </div>

                    <div className="registro-field">
                        <label htmlFor="correo">Correo</label>
                        <input
                            id="correo"
                            type="email"
                            name="correo"
                            placeholder="ana@ejemplo.com"
                            value={datosFormulario.correo}
                            onChange={manejarCambio}
                            required
                        />
                    </div>

                    <div className="registro-field">
                        <label htmlFor="nombreCompleto">Nombre completo</label>
                        <input
                            id="nombreCompleto"
                            type="text"
                            name="nombreCompleto"
                            placeholder="Ana Lee"
                            value={datosFormulario.nombreCompleto}
                            onChange={manejarCambio}
                        />
                    </div>

                    <button type="submit" className="registro-btn" disabled={cargando}>
                        {cargando ? 'Creando cuenta...' : 'Crear cuenta'}
                    </button>

                </form>

                <p className="registro-hint">
                    Demo: usa cualquier nombre de usuario
                </p>

            </div>

            {/* características */}
            <div className="registro-features">

                <div className="registro-feature">
                    <span>📖</span>
                    <span>Descubre<br />libros</span>
                </div>

                <div className="registro-feature">
                    <span>📖</span>
                    <span>Comparte<br />reseñas</span>
                </div>

                <div className="registro-feature">
                    <span>📖</span>
                    <span>Conecta con<br />lectores</span>
                </div>

            </div>
        </div>
    );
}

export default Registro;
