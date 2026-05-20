import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registrarUsuario } from '../api/Usuarios';
import Logo from '../componentes/comunes/Logo';
import libro from '../estilos/img/iconos/libro.png'; 
import estrella from '../estilos/img/iconos/estrella.png'; 
import personas from '../estilos/img/iconos/personas.png';

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
        <div className="bg-crema-fondo dark:bg-dark-fondo transition-colors duration-300 min-h-screen flex flex-col items-center justify-center py-8 px-4">

            {/* enlace de regreso */}
            <button className="self-start sm:ml-[calc(50%-220px)] mb-5 flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400 opacity-70 hover:opacity-100 transition-opacity cursor-pointer bg-transparent border-none p-0" onClick={() => navigate('/')}>
                &larr; Volver al inicio
            </button>

            {/* marca */}
            <div className="text-center mb-6">
                <Logo />
                <p className="text-sm text-gray-600 dark:text-gray-400 mt-2 mb-6">Tu comunidad literaria</p>
            </div>

            {/* tarjeta */}
            <div className="bg-white dark:bg-dark-borde rounded-[12px] shadow-lg p-9 w-full max-w-[440px]">

                {/* pestañas */}
                <div className="flex gap-2 mb-7">
                    <button
                        type="button"
                        className="flex-1 p-2 rounded-lg font-sans text-sm bg-gray-200 dark:bg-gray-700 text-gray-500 hover:bg-gray-300 dark:hover:bg-gray-600 transition-all cursor-pointer"
                        onClick={() => navigate('/login')}
                    >
                         Iniciar sesión
                    </button>

                    <button
                        type="button"
                        className="flex-1 p-2 rounded-lg font-sans text-sm bg-[#b58841] dark:bg-navy-letter text-white cursor-pointer transition-all"
                        onClick={() => setPestana('registro')}
                    >
                         Registrarse
                    </button>
                </div>

                {/* formulario */}
                <form className="flex flex-col gap-5" onSubmit={manejarEnvio}>

                    <div className="flex flex-col gap-2">
                        <label htmlFor="nombreUsuario" className="text-sm text-gray-700 dark:text-gray-300" >Usuario</label>
                        <input
                            id="nombreUsuario"
                            className="bg-transparent border border-gray-300 dark:border-gray-600 rounded-lg py-3 px-4 font-sans text-sm text-gray-800 dark:text-white outline-none focus:border-navy-letter transition-colors placeholder:text-gray-400"                            
                            type="text"
                            name="nombreUsuario"
                            placeholder="ana_lee"
                            value={datosFormulario.nombreUsuario}
                            onChange={manejarCambio}
                            required
                        />
			{errores.nombreUsuario && (
			    <small className="text-red-500 text-xs mt-0.5">{errores.nombreUsuario}</small>
			)}
                    </div>

                     <div className="flex flex-col gap-2">
                        <label htmlFor="correo" className="text-sm text-gray-700 dark:text-gray-300">Correo</label>
                        <input
                            id="correo"
                            className="bg-transparent border border-gray-300 dark:border-gray-600 rounded-lg py-3 px-4 font-sans text-sm text-gray-800 dark:text-white outline-none focus:border-navy-letter transition-colors placeholder:text-gray-400"
                            type="email"
                            name="correo"
                            placeholder="ana@ejemplo.com"   
                            value={datosFormulario.correo}
                            onChange={manejarCambio}
                            required
                        />
			{errores.correo && (
			    <small className="text-red-500 text-xs mt-0.5">{errores.correo}</small>
			)}
                    </div>

			       
                    <div className="flex flex-col gap-2">
                        <label htmlFor="contrasena" className="text-sm text-gray-700 dark:text-gray-300">Contraseña</label>
                        <input
                            id="contrasena"
                            className="bg-transparent border border-gray-300 dark:border-gray-600 rounded-lg py-3 px-4 font-sans text-sm text-gray-800 dark:text-white outline-none focus:border-navy-letter transition-colors placeholder:text-gray-400"
                            type="password"
                            name="contrasena"
                            placeholder="••••••••"
                            value={datosFormulario.contrasena}
                            onChange={manejarCambio}
                            required
                        />
			{errores.contrasena && (
			    <small className="text-red-500 text-xs mt-0.5">{errores.contrasena}</small>
			)}
                    </div>

                    <button type="submit" className="mt-2 py-3 px-4 rounded-lg font-semibold text-white cursor-pointer transition-all duration-200 bg-gold-button hover:bg-gold-button dark:bg-navy-button dark:hover:bg-navy-button-hover hover:scale-105 disabled:opacity-50 disabled:cursor-not-allowed" disabled={cargando}>
                        {cargando ? 'Creando cuenta...' : 'Crear cuenta'}
                    </button>		   
		    {errores.general && (
			<p className="text-red-500 text-sm text-center mb-4">{errores.general}</p>
		    )}
                    {mensajeExito && (
                        <p className="text-green-500 font-semibold text-sm text-center mb-4" style={{ color: 'green', fontWeight: 'bold', marginTop: '10px' }}>
                            {mensajeExito}
                        </p>
                    )}
		    
                </form>

            </div>

            {/* características */}
            <div className="flex justify-center gap-8 sm:gap-12 mt-10 text-navy-letter dark:text-gray-400 text-xs text-center opacity-80">
                    <div className="flex flex-col items-center gap-2">
                      <img 
                        src={libro} 
                        alt="Libro" 
                        className="h-5 w-auto dark:brightness-200" 
                      />
                      <span className="font-cormorant leading-tight">Descubre<br />libros</span>
                    </div>
                    <div className="flex flex-col items-center gap-2">
                      <img 
                        src={estrella} 
                        alt="Estreha" 
                        className="h-5 w-auto dark:brightness-200" 
                      />
                      <span className="font-cormorant leading-tight">Comparte<br />reseñas</span>
                    </div>
                    <div className="flex flex-col items-center gap-2">
                      <img 
                        src={personas} 
                        alt="Personas" 
                        className="h-5 w-auto dark:brightness-200" 
                      />
                      <span className="font-cormorant leading-tight">Conecta con<br />lectores</span>
                    </div>
            </div>
        </div>
    );
}

export default Registro;
