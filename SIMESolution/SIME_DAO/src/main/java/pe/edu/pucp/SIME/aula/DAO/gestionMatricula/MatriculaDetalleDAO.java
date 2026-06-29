package pe.edu.pucp.SIME.aula.DAO.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;

import java.sql.SQLException;
import java.util.List;

public interface MatriculaDetalleDAO extends BaseDAO<MatriculaDetalle,Integer> {
    MatriculaDetalle obtenerPorAlumno(int idAlumno) throws SQLException;
    List<MatriculaDetalle> listarMatriculasPorAlumno(int idAlumno) throws SQLException;

    boolean existeMatriculaActiva(int idAlumno, int idMatriculaCabecera) throws SQLException;

    int insertarMatriculaAlumnoNuevo(int idAlumno, int idMatriculaCabecera) throws SQLException;
}
