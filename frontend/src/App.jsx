import { BrowserRouter, Routes, Route } from "react-router-dom";
import Registro from "./paginas/Registro"

/**
 * Componente principal de la aplicación.
 * <p>
 * Define la configuración de rutas usando React Router DOM.
 * Este componente actúa como contenedor de navegación de la app.
 * </p>
 *
 * @component
 * @returns {JSX.Element} Aplicación con enrutamiento configurado
 */
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/registro" element={<Registro />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
