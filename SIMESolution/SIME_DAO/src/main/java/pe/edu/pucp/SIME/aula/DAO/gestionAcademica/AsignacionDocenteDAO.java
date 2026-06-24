package pe.edu.pucp.SIME.aula.DAO.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.DTO.ProfesorDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.AsignacionDocente;

import java.sql.SQLException;
import java.util.List;

public interface AsignacionDocenteDAO extends BaseDAO<AsignacionDocente, Integer> {
    List<ProfesorDTO> listarProfesoresPorAula(String nivel, String grado, int anio) throws SQLException;
}
