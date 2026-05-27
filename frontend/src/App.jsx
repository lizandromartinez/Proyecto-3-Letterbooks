import { BrowserRouter, Routes, Route } from "react-router-dom";
import Registro from "./paginas/Registro";
import ManejadorVistas from "./ManejadorVistas";
import PaginaAterrizaje from "./paginas/PaginaAterrizaje";
import InicioDeSesion from "./paginas/InicioDeSesion";
import PaginaAterrizajeAutenticado from "./paginas/PaginaAterrizajeAutenticado";
import Perfil from "./paginas/Perfil";
import PerfilPublico from "./paginas/PerfilPublico";
import RegistrarLibro from "./paginas/RegistrarLibro";
import Biblioteca from "./paginas/Biblioteca";  
import DetalleLibro from './paginas/DetalleLibro';
import LayoutScroll from './componentes/navegacion/LayoutScroll';

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
                <Route element={<LayoutScroll />}>
                    <Route path="/" element={<PaginaAterrizaje />} />
                    <Route path="/paginaAterrizaje" element={<PaginaAterrizaje />} />
                    <Route path="/login" element={<InicioDeSesion />} />
                    <Route path="/registro" element={<Registro />} />
                    <Route path="/dashboard" element={<PaginaAterrizajeAutenticado />} />
                    <Route path="/perfil" element={<Perfil />} />
                    <Route path="/usuario/:nombreUsuario" element={<PerfilPublico />} />
                    <Route path="/registrarLibro" element={<RegistrarLibro />} />
                    <Route path="/biblioteca" element={<Biblioteca />} />
                    <Route path="/libro/:id" element={<DetalleLibro />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
