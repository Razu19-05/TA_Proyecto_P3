package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AsignacionDocenteDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.AsignacionDocente;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

import java.sql.*;

public class AsignacionDocenteDAOImpl implements AsignacionDocenteDAO {
    @Override
    public AsignacionDocente load(Integer integer) throws SQLException {
        String sql = """
                SELECT id_asignacion_docente,
                id_persona,
                id_matricula,es_tutor, observacion,activo FROM SIME_ASIGNACION_DOCENTE WHERE
                id_asignacion_docente = ?
                """;
        Connection connection = TransactionContext.getConnection();
        try (
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, integer);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    AsignacionDocente asignacion = new AsignacionDocente();
                    asignacion.setIdAsignacionDocente(rs.getInt("id_asignacion_docente"));

                    asignacion.setEsTutor(rs.getBoolean("es_tutor"));
                    asignacion.setObservacion(rs.getString("observacion"));
                    asignacion.setActivo(rs.getBoolean("activo"));

                    // Cascarón para la Persona (Docente)
                    Persona persona = new Persona();
                    persona.setIdPersona(rs.getInt("id_persona"));
                    asignacion.setPersona(persona);

                    // Cascarón para la Matrícula
                    MatriculaCabecera matricula = new MatriculaCabecera();
                    matricula.setIdMatriculaCabecera(rs.getInt("id_matricula"));
                    asignacion.setMatriculaCabecera(matricula);
                    return asignacion;
                }
            }
        }
        return null;
    }

    @Override
    public AsignacionDocente save(AsignacionDocente asignacionDocente) throws SQLException {
        String sql = """
                INSERT INTO SIME_ASIGNACION_DOCENTE 
                (id_persona, id_matricula, es_tutor, observacion, activo) 
                VALUES (?, ?, ?, ?, ?)
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Extraemos los IDs de los objetos anidados
            pstm.setInt(1, asignacionDocente.getPersona().getIdPersona());
            pstm.setInt(2, asignacionDocente.getMatriculaCabecera().getIdMatriculaCabecera());

            pstm.setBoolean(3, asignacionDocente.isEsTutor());
            pstm.setString(4, asignacionDocente.getObservacion());
            pstm.setBoolean(5, asignacionDocente.isActivo());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        asignacionDocente.setIdAsignacionDocente(newId);
                    }
                }
            }
        }
        return asignacionDocente;
    }

    @Override
    public AsignacionDocente update(AsignacionDocente asignacionDocente) throws SQLException {
        String sql = """
                UPDATE SIME_ASIGNACION_DOCENTE 
                SET id_persona = ?, id_matricula = ?, es_tutor = ?, observacion = ?, activo = ? 
                WHERE id_asignacion_docente = ?
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, asignacionDocente.getPersona().getIdPersona());
            pstm.setInt(2, asignacionDocente.getMatriculaCabecera().getIdMatriculaCabecera());
            pstm.setBoolean(3, asignacionDocente.isEsTutor());
            pstm.setString(4, asignacionDocente.getObservacion());
            pstm.setBoolean(5, asignacionDocente.isActivo());

            pstm.setInt(6, asignacionDocente.getIdAsignacionDocente());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró la asignacion docent con ID: " + asignacionDocente.getIdAsignacionDocente());
            }
        }
        return asignacionDocente;
    }

    @Override
    public void remove(AsignacionDocente asignacionDocente) throws SQLException {
        String sql = "UPDATE SIME_ASIGNACION_DOCENTE SET activo = 0 WHERE id_asignacion_docente = ?";

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, asignacionDocente.getIdAsignacionDocente());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró la asignacion docent con ID: " + asignacionDocente.getIdAsignacionDocente());
            }
        }
    }
}
