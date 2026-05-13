import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'

/**
 * Renderiza la aplicación React en el DOM.
 * <p>
 * Se monta el componente <App /> dentro del elemento HTML con id "root".
 * </p>
 */
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
