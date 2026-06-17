package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;

public interface IMatriculaBL
{
    void procesarMatriculaCompleta(SolicitudMatriculaDTO solicitud) throws Exception;

    int verificarVacantesDisponibles(int idMatriculaCabecera) throws Exception;

    int verificarVacantesPorNivelGrado(String nivel, String grado) throws Exception;
}
