package pe.edu.pucp.SIME.aula.DAO.gestionAlumnos;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.DTO.ApoderadoNuevoDTO;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.sql.SQLException;
import java.util.List;

public interface RelacionFamiliarDAO extends BaseDAO <RelacionFamiliar, Integer> {
    int contarApoderadosActivos(int idAlumno) throws SQLException;
    List<RelacionFamiliar> listarApoderadosActivos(Integer idAlumno) throws SQLException;
    int insertarApoderadoAlumno(int idAlumno, ApoderadoNuevoDTO apoderado) throws SQLException;
    void actualizarApoderado(int idRelacionFamiliar, ApoderadoNuevoDTO apoderado) throws SQLException;
}
