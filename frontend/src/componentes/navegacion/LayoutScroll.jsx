import { useLayoutEffect } from 'react';
import { useLocation, useOutlet } from 'react-router-dom';
import { restablecerScroll } from '../../utilidades/restablecerScroll';

/**
 * Layout de rutas que restablece el scroll después de montar la página destino.
 * Al ser padre del outlet, su useLayoutEffect corre tras los hijos.
 */
const LayoutScroll = () => {
    const location = useLocation();
    const outlet = useOutlet();

    useLayoutEffect(() => {
        restablecerScroll();
    }, [location.pathname, location.key]);

    return outlet;
};

export default LayoutScroll;
