package pe.edu.pucp.SIME.aula.DAO.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.DTO.AulaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;

import java.sql.SQLException;
import java.util.List;

public interface AulaDAO extends BaseDAO<Aula,Integer> {
    List<AulaDTO> listarAulas(String periodo, String nivel, String grado, String estado, String codigo) throws SQLException;

    AulaDTO obtenerDetalleAula(int idAula) throws SQLException;

    boolean existeCodigoAula(String codigo, int idIgnorar) throws SQLException;

    boolean actualizarCapacidad(int idAula, int capacidad) throws SQLException;
}
