package pe.edu.pucp.SIME.aula.DAO.gestionAlumnos;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

import java.sql.SQLException;
import java.util.List;

public interface AlumnoDAO extends BaseDAO <Alumno,Integer> {
    List<Alumno> listarAlumnos() throws SQLException;
}
