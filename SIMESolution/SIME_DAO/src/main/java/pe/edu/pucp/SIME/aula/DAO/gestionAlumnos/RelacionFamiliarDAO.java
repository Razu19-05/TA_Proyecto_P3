package pe.edu.pucp.SIME.aula.DAO.gestionAlumnos;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;

import java.sql.SQLException;

public interface RelacionFamiliarDAO extends BaseDAO <RelacionFamiliar, Integer> {
    int contarApoderadosActivos(int idAlumno) throws SQLException;
}
