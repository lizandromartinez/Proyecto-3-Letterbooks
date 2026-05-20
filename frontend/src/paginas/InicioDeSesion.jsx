import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { ContextoSesion } from '../contexto/Sesion';
import { peticionLogin } from '../api/Usuarios';
import { validarNombreUsuario, validarContrasena } from '../utilidades/Validadores';
import Logo from '../componentes/comunes/Logo';
import libro from '../estilos/img/iconos/libro.png'; 
import estrella from '../estilos/img/iconos/estrella.png'; 
import personas from '../estilos/img/iconos/personas.png';

/**
 * Componente que renderiza el formulario de inicio de sesión.
 */
const InicioDeSesion = () => {
  const { iniciarSesion } = useContext(ContextoSesion);
  const navigate = useNavigate();

  const [credenciales, setCredenciales] = useState({
    nombreUsuario: '',
    contrasena: ''
  });
  const [error, setError] = useState('');

  /**
   * Actualiza el estado cuando el usuario escribe en los inputs.
   * @param {Object} e Evento del input.
   */
  const manejarCambio = (e) => {
    setCredenciales({
      ...credenciales,
      [e.target.name]: e.target.value
    });
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
      navigate('/dashboard');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="bg-crema-fondo dark:bg-dark-fondo transition-colors duration-300 min-h-screen flex flex-col items-center justify-center py-8 px-4">
      <button className="self-start sm:ml-[calc(50%-220px)] mb-5 flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400 opacity-70 hover:opacity-100 transition-opacity cursor-pointer bg-transparent border-none p-0" onClick={() => navigate('/')}>
        &larr; Volver al inicio
      </button>

      <div className="text-center mb-6">
        <Logo />
        <p className="text-sm text-gray-600 dark:text-gray-400 mt-2 mb-6">Tu comunidad literaria</p>
      </div>

      <div className="bg-white dark:bg-dark-borde rounded-[12px] shadow-lg p-9 w-full max-w-[440px]">
        <div className="flex gap-2 mb-7"> 
          <button className="flex-1 p-2 rounded-lg font-sans text-sm bg-[#b58841] dark:bg-navy-letter text-white cursor-pointer transition-all">
            Iniciar sesión
          </button>
          <button className="flex-1 p-2 rounded-lg font-sans text-sm bg-gray-200 dark:bg-gray-700 text-gray-500 hover:bg-gray-300 dark:hover:bg-gray-600 transition-all cursor-pointer" onClick={() => navigate("/registro")}>
            Registrarse
          </button>
        </div>

        {error && <p className="text-red-500 text-sm text-center mb-4">{error}</p>}

        <form className="flex flex-col gap-5" onSubmit={manejarEnvio}>
          <div className="flex flex-col gap-2">
            <label className="text-sm text-gray-700 dark:text-gray-300">Usuario</label>
            <input
              className="bg-transparent border border-gray-300 dark:border-gray-600 rounded-lg py-3 px-4 font-sans text-sm text-gray-800 dark:text-white outline-none focus:border-navy-letter transition-colors placeholder:text-gray-400"
              type="text"
              name="nombreUsuario"
              placeholder="lector001"
              value={credenciales.nombreUsuario}
              onChange={manejarCambio}
            />
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-sm text-gray-700 dark:text-gray-300">Contraseña</label>
            <input
              className="bg-transparent border border-gray-300 dark:border-gray-600 rounded-lg py-3 px-4 font-sans text-sm text-gray-800 dark:text-white outline-none focus:border-navy-letter transition-colors placeholder:text-gray-400"
              type="password"
              name="contrasena"
              placeholder="••••••••"
              value={credenciales.contrasena}
              onChange={manejarCambio}
            />
          </div>

          <button type="submit" className="mt-2 py-3 px-4 rounded-lg font-semibold text-white cursor-pointer transition-all duration-200 bg-gold-button hover:bg-gold-button dark:bg-navy-button dark:hover:bg-navy-button-hover hover:scale-110">
            Iniciar sesión
          </button>
        </form>
      </div>

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
};

export default InicioDeSesion;