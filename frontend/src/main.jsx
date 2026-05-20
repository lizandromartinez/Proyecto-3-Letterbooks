import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import { ProveedorSesion } from './contexto/Sesion'
import './estilos/Globales.css'

/**
 * Renderiza la aplicación React en el DOM.
 * <p>
 * Se monta el componente <App /> dentro del elemento HTML con id "root".
 * </p>
 */
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ProveedorSesion>
      <App />
    </ProveedorSesion>
  </StrictMode>,
)
