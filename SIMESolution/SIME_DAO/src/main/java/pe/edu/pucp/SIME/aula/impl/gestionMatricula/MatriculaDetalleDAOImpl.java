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
        String sql = """
                INSERT INTO SIME_MATRICULA_DETALLE
                (id_matricula_cabecera, id_alumno, fecha_matricula, estado, activo)
                VALUES (?, ?, ?, ?, ?)
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, matriculaDetalle.getMatriculaCabecera().getIdMatriculaCabecera());
            stmt.setInt(2, matriculaDetalle.getAlumno().getIdAlumno());
            if (matriculaDetalle.getFechaMatricula() != null) {
                stmt.setDate(3, new java.sql.Date(matriculaDetalle.getFechaMatricula().getTime()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setString(4, matriculaDetalle.getEstado().name());

            stmt.setBoolean(5, matriculaDetalle.isActivo());

            int affectedRows = stmt.executeUpdate();

            if(affectedRows > 0){
                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        matriculaDetalle.setIdMatriculaDetalle(newId);
                    }
                }
            }
        }
        return matriculaDetalle;
    }

    @Override
    public MatriculaDetalle update(MatriculaDetalle matriculaDetalle) throws SQLException {
        String sql = """
                UPDATE SIME_MATRICULA_DETALLE 
                SET id_matricula_cabecera = ?, id_alumno = ?, fecha_matricula = ?, estado = ?
                WHERE id_matricula_detalle = ?
                """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, matriculaDetalle.getMatriculaCabecera().getIdMatriculaCabecera());
            stmt.setInt(2, matriculaDetalle.getAlumno().getIdAlumno());

            if (matriculaDetalle.getFechaMatricula() != null) {
                stmt.setDate(3, new java.sql.Date(matriculaDetalle.getFechaMatricula().getTime()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }
            stmt.setString(4, matriculaDetalle.getEstado().name());
            // Parámetro del WHERE
            stmt.setInt(5, matriculaDetalle.getIdMatriculaDetalle());

            int affectedRows = stmt.executeUpdate();

            if(affectedRows == 0){
                System.out.println("No se encontró el concepto de MATRICULADETALLE con ID: " + matriculaDetalle.getIdMatriculaDetalle());
            }
        }
        return matriculaDetalle;
    }

    @Override
    public void remove(MatriculaDetalle matriculaDetalle) throws SQLException {
        String sql = "UPDATE SIME_MATRICULA_DETALLE SET activo = 0 WHERE id_matricula_detalle = ?";

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, matriculaDetalle.getIdMatriculaDetalle());
            int affectedRows = stmt.executeUpdate();

            if(affectedRows == 0){
                System.out.println("No se encontró el concepto de MATRICULADETALLE con ID: " + matriculaDetalle.getIdMatriculaDetalle());
            }
        }
    }
}
