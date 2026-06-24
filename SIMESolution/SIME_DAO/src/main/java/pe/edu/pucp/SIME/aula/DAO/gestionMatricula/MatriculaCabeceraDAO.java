package pe.edu.pucp.SIME.aula.DAO.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

import java.sql.SQLException;

public interface MatriculaCabeceraDAO extends BaseDAO<MatriculaCabecera,Integer> {
	MatriculaCabecera obtenerPorGradoSeccionActivo(int idGradoSeccion) throws SQLException;
}
