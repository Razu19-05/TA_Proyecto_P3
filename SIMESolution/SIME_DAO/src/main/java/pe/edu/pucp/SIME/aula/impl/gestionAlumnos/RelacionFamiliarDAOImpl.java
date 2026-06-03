package pe.edu.pucp.SIME.aula.impl.gestionAlumnos;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionAlumnos.TipoRelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.sql.*;

public class RelacionFamiliarDAOImpl implements RelacionFamiliarDAO {
    @Override
    public RelacionFamiliar load(Integer rf_Id) throws SQLException {
        String sql = """
                SELECT 
                id_relacion_familiar,id_alumno, id_persona, parentesco, contacto_emergencia,observaciones, activo
                from SIME_RELACION_FAMILIAR where id_relacion_familiar = ?
                """;

        Connection conn = TransactionContext.getConnection();
        try (PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1,rf_Id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    RelacionFamiliar relacion = new RelacionFamiliar();
                    relacion.setIdRelacionFamiliar(rs.getInt(1));
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(rs.getInt("id_alumno"));
                    relacion.setAlumnos(alumno);
                    Persona persona = new Persona();
                    persona.setIdPersona(rs.getInt("id_persona"));
                    relacion.setPersona(persona);
                    relacion.setContactoEmergencia(rs.getBoolean("contacto_emergencia"));
                    relacion.setParentesco(TipoRelacionFamiliar.valueOf(rs.getString("parentesco")));
                    relacion.setObservaciones(rs.getString("observaciones"));
                    relacion.setActivo(rs.getBoolean("activo"));
                    return relacion;
                }
            }
        }
        return null;
    }

    @Override
    public RelacionFamiliar save(RelacionFamiliar relacion) throws SQLException {
        String sql = """
                INSERT INTO SIME_RELACION_FAMILIAR 
                (id_alumno, id_persona, parentesco, contacto_emergencia,observaciones, activo) 
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, relacion.getAlumno().getIdAlumno());
            pstm.setInt(2, relacion.getPersona().getIdPersona());

            // 2. Convertimos el Enum a texto
            pstm.setString(3, relacion.getParentesco().name());
            pstm.setInt(4,1);
            pstm.setString(5, relacion.getObservaciones());
            pstm.setBoolean(6, relacion.isActivo());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        relacion.setIdRelacionFamiliar(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return relacion;
    }

    @Override
    public RelacionFamiliar update(RelacionFamiliar relacionFamiliar) throws SQLException {
        String sql = """
                UPDATE SIME_RELACION_FAMILIAR SET 
                observaciones = ? WHERE id_relacion_familiar = ?
                """;
        Connection conn = TransactionContext.getConnection();
        try(PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, relacionFamiliar.getObservaciones());
            pstm.setInt(2,relacionFamiliar.getIdRelacionFamiliar());

            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No se encontró la relación para actualizar.");
                return null;
            }
            return relacionFamiliar;
        }
    }

    @Override
    public void remove(RelacionFamiliar relacionFamiliar) throws SQLException {
        String sql = "UPDATE SIME_RELACION_FAMILIAR SET activo = 0 WHERE id_relacion_familiar = ?";
        relacionFamiliar.setActivo(false);

        Connection conn = TransactionContext.getConnection();
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {

            // Extraemos los IDs de los objetos anidados
            pstm.setInt(1, relacionFamiliar.getIdRelacionFamiliar());;

            int filasAfectadas = pstm.executeUpdate();

            if (filasAfectadas == 0) {
                System.out.println("No se encontró la relación para eliminar.");
            } else {
                System.out.println("Relación eliminada exitosamente.");
            }

        }
    }
}
