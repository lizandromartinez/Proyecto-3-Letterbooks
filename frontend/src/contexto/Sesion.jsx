import React, { createContext, useState, useEffect } from 'react';

// Creación del contexto
export const ContextoSesion = createContext();

/**
 * Componente proveedor del estado global de sesión.
 * Envuelve a los componentes hijos permitiéndoles acceder y modificar el token.
 * @param {Object} props Propiedades del componente (children).
 * @return {JSX.Element} El proveedor del contexto de sesión.
 */
export const ProveedorSesion = ({ children }) => {
    const [token, setToken] = useState(null);

    // Al cargar la aplicación, verificamos si ya hay un token guardado
    useEffect(() => {
        const tokenGuardado = localStorage.getItem('token');
        if (tokenGuardado) {
            setToken(tokenGuardado);
        }
    }, []);

    /**
     * Guarda el token en el estado y en el almacenamiento local.
     * @param {string} nuevoToken El token JWT recibido del servidor.
     */
    const iniciarSesion = (nuevoToken) => {
        setToken(nuevoToken);
        localStorage.setItem('token', nuevoToken);
    };

    /**
     * Borra el token del estado y del almacenamiento local.
     */
    const cerrarSesion = () => {
        setToken(null);
        localStorage.removeItem('token');
    };

    return (
        <ContextoSesion.Provider value={{ token, iniciarSesion, cerrarSesion }}>
            {children}
        </ContextoSesion.Provider>
    );
};
