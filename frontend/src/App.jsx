import { BrowserRouter, Routes, Route } from "react-router-dom";
import Registro from "./paginas/Registro";
import ManejadorVistas from "./ManejadorVistas";
import PaginaAterrizaje from "./paginas/PaginaAterrizaje";
import InicioDeSesion from "./paginas/InicioDeSesion";
import PaginaAterrizajeAutenticado from "./paginas/PaginaAterrizajeAutenticado";

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
        <Route path="/" element={<PaginaAterrizaje />} />
        <Route path="/paginaAterrizaje" element={<PaginaAterrizaje />} />
        <Route path="/login" element={<InicioDeSesion />} />
        <Route path="/registro" element={<Registro />} />
        <Route path="/dashboard" element={<PaginaAterrizajeAutenticado />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
