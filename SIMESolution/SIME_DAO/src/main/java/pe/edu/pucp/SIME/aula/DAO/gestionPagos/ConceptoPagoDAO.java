package pe.edu.pucp.SIME.aula.DAO.gestionPagos;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.DTO.PagoMatriculaDTO;
import pe.edu.pucp.SIME.model.gestionPagos.ConceptoPago;

import java.sql.SQLException;
import java.util.List;

public interface ConceptoPagoDAO extends BaseDAO<ConceptoPago, Integer> {
    List<PagoMatriculaDTO> listarConceptosAlumnoNuevo() throws SQLException;
}
