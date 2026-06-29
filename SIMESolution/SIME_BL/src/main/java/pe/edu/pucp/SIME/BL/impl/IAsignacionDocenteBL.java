package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.AsignacionDocenteRequestDTO;
import pe.edu.pucp.SIME.model.DTO.ProfesorAulaDTO;

import java.util.List;

public interface IAsignacionDocenteBL {

    List<ProfesorAulaDTO> listarPorAula(int idAula) throws Exception;

    List<ProfesorAulaDTO> listarPorMatriculaCabecera(int idMatriculaCabecera) throws Exception;

    List<ProfesorAulaDTO> listarProfesoresDisponibles(String criterio) throws Exception;

    int asignarDocente(AsignacionDocenteRequestDTO request) throws Exception;

    boolean actualizarAsignacion(int idAsignacionDocente, AsignacionDocenteRequestDTO request) throws Exception;

    boolean eliminarAsignacion(int idAsignacionDocente) throws Exception;
}