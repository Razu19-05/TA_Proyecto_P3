package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;

public interface IMatriculaBL
{
    void procesarMatriculaCompleta(SolicitudMatriculaDTO solicitud) throws Exception;

    int verificarVacantesDisponibles(int idMatriculaCabecera) throws Exception;

    int verificarVacantesPorNivelGrado(String nivel, String grado) throws Exception;

    MatriculaDetalle insertarMatriculaDetalle(MatriculaDetalle matriculaDetalle) throws Exception;

    MatriculaDetalle cargarMatriculaAlumno(int idAlumno) throws Exception;
}
