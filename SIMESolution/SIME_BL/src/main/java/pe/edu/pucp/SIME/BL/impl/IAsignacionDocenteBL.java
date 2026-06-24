package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.ProfesorDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.AsignacionDocente;

import java.util.List;

public interface IAsignacionDocenteBL
{
    List<ProfesorDTO> obtenerProfesoresPorAula(String nivel, String grado, int anio) throws Exception;
    AsignacionDocente actualizarAsignacionDocente(AsignacionDocente asignacionDocente) throws Exception;
    void eliminarAsignacionDocente(AsignacionDocente asignacionDocente) throws Exception;
}
