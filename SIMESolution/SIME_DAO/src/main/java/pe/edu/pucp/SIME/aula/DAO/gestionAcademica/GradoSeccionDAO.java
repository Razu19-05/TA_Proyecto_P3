package pe.edu.pucp.SIME.aula.DAO.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;

import java.sql.SQLException;

public interface GradoSeccionDAO extends BaseDAO<GradoSeccion, Integer> {
	GradoSeccion buscarPorNivelYGrado(String nivel, String grado) throws SQLException;
	int obtenerIdPorNivelYGrado(String nivel, String grado) throws SQLException;
}
