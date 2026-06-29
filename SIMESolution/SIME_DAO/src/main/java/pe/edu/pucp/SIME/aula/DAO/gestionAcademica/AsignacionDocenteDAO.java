package pe.edu.pucp.SIME.aula.DAO.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.DTO.AsignacionDocenteRequestDTO;
import pe.edu.pucp.SIME.model.DTO.ProfesorAulaDTO;
import pe.edu.pucp.SIME.model.DTO.ProfesorDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.AsignacionDocente;

import java.sql.SQLException;
import java.util.List;

public interface AsignacionDocenteDAO extends BaseDAO<AsignacionDocente, Integer> {
    List<ProfesorAulaDTO> listarPorAula(int idAula) throws SQLException;

    List<ProfesorAulaDTO> listarPorMatriculaCabecera(int idMatriculaCabecera) throws SQLException;

    List<ProfesorAulaDTO> listarProfesoresDisponibles(String criterio) throws SQLException;

    int obtenerMatriculaCabeceraActivaPorAula(int idAula) throws SQLException;

    boolean existeAsignacionActiva(int idPersona, int idMatriculaCabecera, int idIgnorar) throws SQLException;

    boolean existeTutorActivo(int idMatriculaCabecera, int idIgnorar) throws SQLException;

    int asignarDocente(AsignacionDocenteRequestDTO request) throws SQLException;

    boolean actualizarAsignacion(int idAsignacionDocente, AsignacionDocenteRequestDTO request) throws SQLException;

    boolean eliminarAsignacion(int idAsignacionDocente) throws SQLException;
}
