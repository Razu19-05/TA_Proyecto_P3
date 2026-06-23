package pe.edu.pucp.SIME.aula.DAO.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;

import java.sql.SQLException;

public interface MatriculaDetalleDAO extends BaseDAO<MatriculaDetalle,Integer> {
    MatriculaDetalle obtenerPorAlumno(int idAlumno) throws SQLException;
}
