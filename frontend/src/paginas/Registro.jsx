import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
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
     * Estado que almacena los errores de validación del formulario.     
     */
    const [errores, setErrores] = useState({});

    /**
     * Hook para navegar a otras rutas
     */
    const navigate = useNavigate();

    /**
     * Estado que almacena el mensaje de éxito tras el registro
     */
    const [mensajeExito, setMensajeExito] = useState('');
    
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
	const { name, value } = evento.target;

	setDatosFormulario(prev => ({
            ...prev,
            [name]: value
	}));

	setErrores(prev => ({
            ...prev,
            [name]: undefined
	}));
    };
    
    /**
     * Maneja el envío del formulario de registro.
     * <p>
     * Valida el correo, limpia los datos y envía la solicitud al backend.
     * Muestra mensajes de éxito o error según la respuesta.
     * </p>
     *
     * @param {<HTMLFormElement>} evento evento de envío
     */
    const manejarEnvio = async (evento) => {
        evento.preventDefault();

	const nuevosErrores = {};

	const correoValido =
            /^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/.test(datosFormulario.correo);

	if (!correoValido) {
            nuevosErrores.correo = 'Ingresa un correo válido';
	}

	if (!datosFormulario.nombreUsuario.trim()) {
            nuevosErrores.nombreUsuario = 'El usuario es obligatorio';
	}

	if (!datosFormulario.contrasena.trim()) {
            nuevosErrores.contrasena = 'La contraseña es obligatoria';
	}

	if (Object.keys(nuevosErrores).length > 0) {
            setErrores(nuevosErrores);
            return;
	}

	setErrores({});
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

            setDatosFormulario({
		nombreUsuario: '',
		correo: '',
		contrasena: '',
		nombreCompleto: ''
            });

            setMensajeExito('¡Registro exitoso! Redirigiendo al inicio de sesión...');
            setTimeout(() => {
                navigate('/login');
            }, 2000);

	} catch (error) {
            const nuevosErroresBackend = {};

            if (typeof error === 'object') {
		Object.entries(error).forEach(([campo, mensaje]) => {
                    nuevosErroresBackend[campo] = mensaje;
		});
            } else {
		nuevosErroresBackend.general = error;
            }

            setErrores(nuevosErroresBackend);

	} finally {
            setCargando(false);
	}
    };

    return (
        <div className="registro-page">

            {/* enlace de regreso */}
            <a href="/" className="registro-back" onClick={(e) => { e.preventDefault(); navigate('/'); }}>← Volver al inicio</a>

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
                        onClick={() => navigate('/login')}
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
			{errores.nombreUsuario && (
			    <small className="error">{errores.nombreUsuario}</small>
			)}
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
			{errores.contrasena && (
			    <small className="error">{errores.contrasena}</small>
			)}
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
			{errores.correo && (
			    <small className="error">{errores.correo}</small>
			)}
                    </div>

                    <button type="submit" className="registro-btn" disabled={cargando}>
                        {cargando ? 'Creando cuenta...' : 'Crear cuenta'}
                    </button>		   
		    {errores.general && (
			<p className="error-general">{errores.general}</p>
		    )}
                    {mensajeExito && (
                        <p className="exito-general" style={{ color: 'green', fontWeight: 'bold', marginTop: '10px' }}>
                            {mensajeExito}
                        </p>
                    )}
		    
                </form>

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
