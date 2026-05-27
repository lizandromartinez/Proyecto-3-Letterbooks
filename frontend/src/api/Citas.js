import axios from 'axios';

const URL_BASE = 'http://localhost:8080/api/citas';

/**
 * Alterna el estado de un "Me gusta" de una cita.
 * @param {number} idCita id de la cita.
 * @param {string} token token JWT del usuario activo.
 * @return {Promise<Object>} objeto con el estado actualizado { likeActivo: boolean }.
 */
export const alternarLikeCita = async (idCita, token) => {
    const respuesta = await axios.post(`${URL_BASE}/${idCita}/like`, {}, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return respuesta.data;
};
