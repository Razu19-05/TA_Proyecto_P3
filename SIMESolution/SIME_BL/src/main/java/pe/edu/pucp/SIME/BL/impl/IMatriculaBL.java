package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;

public interface IMatriculaBL
{
    void procesarMatriculaCompleta(SolicitudMatriculaDTO solicitud) throws Exception;
}
