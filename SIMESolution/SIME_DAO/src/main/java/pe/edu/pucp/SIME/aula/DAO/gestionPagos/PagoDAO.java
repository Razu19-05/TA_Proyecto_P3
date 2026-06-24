package pe.edu.pucp.SIME.aula.DAO.gestionPagos;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;

import java.sql.SQLException;
import java.util.List;

public interface PagoDAO extends BaseDAO<Pago, Integer> {
    List<Pago> listarPagosdeAlumno(int idMatriduladetalle) throws SQLException;
}
