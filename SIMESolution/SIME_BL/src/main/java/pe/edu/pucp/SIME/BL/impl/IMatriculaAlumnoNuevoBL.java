package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoRequestDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoResponseDTO;
import pe.edu.pucp.SIME.model.DTO.VacanteMatriculaDTO;

import java.util.List;

public interface IMatriculaAlumnoNuevoBL {

    MatriculaAlumnoNuevoResponseDTO matricularAlumnoNuevo(
            MatriculaAlumnoNuevoRequestDTO request
    ) throws Exception;

    List<VacanteMatriculaDTO> listarVacantes(
            String periodo,
            String nivel,
            String grado
    ) throws Exception;
}
