import React, { useState } from 'react';
import subir from '../../estilos/img/iconos/subir.png';

import {
    registrarAutor,
    subirPortada
} from '../../api/Libros';

export default function ModalAgregaAutor({ isOpen, onClose, onSave, token }) {
    const [nombreAutor, setNombreAutor] = useState('');
    const [biografia, setBiografia] = useState('');
    const [fechaNacimiento, setFechaNacimiento] = useState('');
    const [guardando, setGuardando] = useState(false);

    const [foto, setFoto] = useState(null); 
    const [vistaPreviaFoto, setVistaPreviaFoto] = useState(null);

    if (!isOpen) return null;

    const handleCambioFoto = (e) => {
        const archivo = e.target.files[0];
        if (archivo) {
            setFoto(archivo);
            setVistaPreviaFoto(URL.createObjectURL(archivo));           
        }
    };

    const handleEnviar = async (e) => {
	e.preventDefault();
	if (!nombreAutor.trim())
	    return;

	setGuardando(true);
	try {
            let urlFotoFinal = null;
	                
            if (foto) {	
		urlFotoFinal = await subirPortada(foto, token); 
            }
	            
            const datosAutor = {
		nombreAutor: nombreAutor.trim(),
		biografia: biografia.trim() || null,
		fechaNacimiento: fechaNacimiento || null,
		foto: urlFotoFinal 
            };
                        
            const nuevoAutor = await registrarAutor(datosAutor, token); 
            
            if (onSave) {
		onSave(nuevoAutor);
            }
            
            limpiarFormulario();
            onClose(); 
	} catch (err) {
            console.error("Error al procesar el formulario de autor:", err);
            alert(err.message || "Error al crear el autor"); 
	} finally {
            setGuardando(false);
	}
    };
    
    const limpiarFormulario = () => {
        setNombreAutor('');
        setBiografia('');
        setFechaNacimiento('');
        setFoto(null);
        setVistaPreviaFoto(null);
    };

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm animate-fade-in">
            <div className="w-full max-w-2xl bg-white dark:bg-dark-borde border border-amber-900/10 dark:border-white/10 rounded-2xl p-6 shadow-xl font-inter text-navy-letter dark:text-gray-100 transition-colors duration-300">
                
                {/* Header */}
                <div className="flex justify-between items-center mb-4 pb-2 border-b border-gray-100 dark:border-white/5">
                    <h3 className="font-cormorant text-xl font-bold">Crear Autor</h3>
                    <button type="button" onClick={onClose} className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-200 text-2xl focus:outline-none">&times;</button>
                </div>

                <form id="formAutorCompleto" onSubmit={handleEnviar} className="flex flex-col md:flex-row gap-6 text-left">
                    
                    {/* COLUMNA IZQUIERDA: Vista previa de foto */}
                    <div className="w-full md:w-2/5 flex flex-col items-center md:items-start">
                        <span className="font-inter text-xs font-bold text-gray-700 dark:text-gray-300 mb-2 self-start">Fotografía del autor</span> 
                        <label className="w-full h-64 min-h-[256px] border-2 border-dashed border-amber-900/20 dark:border-white/20 rounded-xl flex flex-col items-center justify-center p-4 text-center cursor-pointer hover:bg-gray-50 dark:hover:bg-white/5 transition-all relative overflow-hidden group">
                            <input type="file" accept="image/png, image/jpeg" className="hidden" onChange={handleCambioFoto} disabled={guardando} />
                            {vistaPreviaFoto ? (
                                <img src={vistaPreviaFoto} alt="Vista previa" className="w-full h-full object-cover absolute inset-0" />
                            ) : (
                                <div className="flex flex-col items-center justify-center text-gray-400">
                                    <img src={subir} alt="Subir" className="h-10 w-10 object-contain dark:invert" />
                                    <span className="font-inter text-xs text-gray-600 dark:text-gray-300 font-medium mt-2">Sube la foto del autor</span>
                                </div>
                            )}
                        </label>
                    </div>

                    {/* COLUMNA DERECHA: Inputs */}
                    <div className="w-full md:w-3/5 flex flex-col gap-3 font-inter text-sm">
                        <div className="flex flex-col gap-1">
                            <label htmlFor="inputNombreAutor" className="font-medium text-xs">Nombre completo *</label>
                            <input id="inputNombreAutor" type="text" required placeholder="Ej. Gabriel García Márquez" value={nombreAutor} onChange={(e) => setNombreAutor(e.target.value)} disabled={guardando} maxLength={150} className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent focus:outline-none focus:ring-1 focus:ring-gold-button text-sm" />
                        </div>

                        <div className="flex flex-col gap-1">
                            <label htmlFor="inputFechaNac" className="font-medium text-xs">Fecha de Nacimiento</label>
                            <input id="inputFechaNac" type="date" value={fechaNacimiento} onChange={(e) => setFechaNacimiento(e.target.value)} disabled={guardando} className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-white dark:bg-dark-borde text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button text-sm" />
                        </div>

                        <div className="flex flex-col gap-1">
                            <label htmlFor="inputBio" className="font-medium text-xs">Biografía Detallada</label>
                            <textarea id="inputBio" placeholder="Escribe una breve semblanza..." value={biografia} onChange={(e) => setBiografia(e.target.value)} disabled={guardando} rows={5} className="w-full p-2.5 border border-gray-300 dark:border-white/10 rounded-lg bg-transparent resize-none focus:outline-none focus:ring-1 focus:ring-gold-button text-sm" />
                        </div>
                    </div>
                </form>

                {/* Botones de acción */}
                <div className="flex gap-3 justify-end mt-5 pt-3 border-t border-gray-100 dark:border-white/5">
                    <button type="button" onClick={onClose} disabled={guardando} className="bg-amber-900/5 text-gray-700 dark:bg-white/5 dark:text-gray-300 px-4 py-2 rounded-lg font-medium hover:bg-amber-900/10 dark:hover:bg-white/10 text-xs cursor-pointer border-none">Cancelar</button>
                    <button type="submit" form="formAutorCompleto" disabled={guardando || !nombreAutor.trim()} className={`px-4 py-2 rounded-lg font-medium text-xs text-white cursor-pointer border-none ${guardando || !nombreAutor.trim() ? 'bg-gray-300 dark:bg-zinc-700 cursor-not-allowed' : 'bg-gold-button dark:bg-navy-button text-white  hover:bg-gold-button-hover dark:hover:bg-navy-button-hover shadow-md'}`}>
                        {guardando ? 'Guardando...' : 'Guardar Autor'}
                    </button>
                </div>
            </div>
        </div>
    );
}
