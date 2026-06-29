package pe.edu.pucp.SIME.aula.DAO.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;

import java.sql.SQLException;

public interface PeriodoAcademicoDAO extends BaseDAO<PeriodoAcademico,Integer> {
    int obtenerIdPorAnio(int anio) throws SQLException;
}
