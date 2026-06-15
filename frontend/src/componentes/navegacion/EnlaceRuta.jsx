import { Link } from 'react-router-dom';
import { scrollAlNavegar } from '../../utilidades/restablecerScroll';

/**
 * Link de React Router que hace scroll a la parte superior antes de navegar.
 */
const EnlaceRuta = ({ onClick, ...props }) => (
    <Link onClick={scrollAlNavegar(onClick)} {...props} />
);

export default EnlaceRuta;
