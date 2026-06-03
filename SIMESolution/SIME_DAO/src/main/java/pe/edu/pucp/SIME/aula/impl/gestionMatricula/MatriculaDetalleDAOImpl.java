package pe.edu.pucp.SIME.aula.impl.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaDetalleDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;
import pe.edu.pucp.SIME.model.gestionMatricula.TipoMatricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaDetalleDAOImpl implements MatriculaDetalleDAO {
    @Override
    public MatriculaDetalle load(Integer id) throws SQLException {
        String sql = "select id_matricula_detalle, id_matricula_cabecera, id_alumno, fecha_matricula, estado, activo " +
                "from SIME_MATRICULA_DETALLE where id_matricula_detalle = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MatriculaDetalle matricula = new MatriculaDetalle();
                    matricula.setIdMatriculaDetalle(rs.getInt("id_matricula_detalle"));

                    MatriculaCabecera matriculaCabecera = new MatriculaCabecera();
                    matriculaCabecera.setIdMatriculaCabecera(rs.getInt("id_matricula_cabecera"));

                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(rs.getInt("id_alumno"));

                    matricula.setMatriculaCabecera(matriculaCabecera);
                    matricula.setAlumno(alumno);
                    matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                    String estado = rs.getString("estado");
                    matricula.setEstado(TipoMatricula.valueOf(estado));
                    matricula.setActivo(rs.getBoolean("activo"));
                    return matricula;
                }
            }
        }
        return null;

    }

    @Override
    public MatriculaDetalle save(MatriculaDetalle matriculaDetalle) throws SQLException {
        return null;
    }

    @Override
    public MatriculaDetalle update(MatriculaDetalle matriculaDetalle) throws SQLException {
        return null;
    }

    @Override
    public void remove(MatriculaDetalle matriculaDetalle) throws SQLException {

    }
}
