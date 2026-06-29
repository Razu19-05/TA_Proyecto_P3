package pe.edu.pucp.SIME.aula.impl.gestionAlumnos;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ApoderadoNuevoDTO;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionAlumnos.TipoRelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionDePersonal.TipoPersona;


import java.sql.*;
import java.util.List;

public class RelacionFamiliarDAOImpl implements RelacionFamiliarDAO {
    public Alumno buscarAlumno (int id) throws SQLException{
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
        Alumno alumno = alumnoDAO.load(id);
        return alumno;
    }
    public Persona buscarPersona(int id) throws SQLException{
        PersonaDAO personaDAO = new PersonaDAOImpl();
        Persona persona = personaDAO.load(id);
        return persona;
    }
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
                    Alumno alumno = buscarAlumno(rs.getInt("id_alumno"));
                    relacion.setAlumnos(alumno);
                    Persona persona = buscarPersona(rs.getInt("id_persona"));
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

    public int contarApoderadosActivos(int idAlumno) throws SQLException {
        String sql = "SELECT COUNT(*) FROM SIME_RELACION_FAMILIAR WHERE id_alumno = ? AND activo = 1";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, idAlumno);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna el número de familiares
                }
            }
        }
        return 0; // Si no hay registros, retorna 0
    }

    @Override
    public List<RelacionFamiliar> listarApoderadosActivos(Integer idAlumno) throws SQLException {
        String sql = "SELECT id_relacion_familiar FROM SIME_RELACION_FAMILIAR WHERE id_alumno = ? AND activo = 1";
        Connection conn = TransactionContext.getConnection();
        List<RelacionFamiliar> apoderados = new java.util.ArrayList<>();
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, idAlumno);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int idPersona = rs.getInt("id_relacion_familiar");
                    // Reutilizamos el DAO de Persona para mapear completamente la persona
                    RelacionFamiliar rf = new RelacionFamiliar();
                    rf.setIdRelacionFamiliar(idPersona);
                    apoderados.add(rf);
                }
            }
        }

        return apoderados;
    }

    @Override
    public int insertarApoderadoAlumno(int idAlumno, ApoderadoNuevoDTO apoderado) throws SQLException {

        Connection connection = TransactionContext.getConnection();

        PersonaDAO personaDAO = new PersonaDAOImpl();

        Persona persona = personaDAO.buscarPorDni(apoderado.getDni());

        int idPersona;

        if (persona == null) {
            idPersona = insertarPersonaApoderado(connection, apoderado);
        } else {
            idPersona = persona.getIdPersona();
        }

        Integer idRelacionExistente = buscarRelacionActiva(connection, idAlumno, idPersona);

        if (idRelacionExistente != null) {
            return idRelacionExistente;
        }

        return insertarRelacionFamiliar(connection, idAlumno, idPersona, apoderado);
    }

    private int insertarPersonaApoderado(Connection connection, ApoderadoNuevoDTO apoderado) throws SQLException {
        String sql = """
        INSERT INTO SIME_PERSONA
        (
            nombres,
            apellido_paterno,
            apellido_materno,
            dni,
            telefono,
            correo,
            direccion,
            tipo,
            especialidad,
            cargo,
            area,
            activo
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)
    """;

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            String tipo = normalizarTipoPersona(apoderado.getTipo());

            pstm.setString(1, apoderado.getNombres());
            pstm.setString(2, apoderado.getApellidoPaterno());
            pstm.setString(3, apoderado.getApellidoMaterno());
            pstm.setString(4, apoderado.getDni());
            pstm.setString(5, apoderado.getTelefono());
            pstm.setString(6, apoderado.getCorreo());
            pstm.setString(7, apoderado.getDireccion());
            pstm.setString(8, tipo);

            if ("PROFESOR".equals(tipo)) {
                pstm.setString(9, apoderado.getEspecialidad());
                pstm.setNull(10, Types.VARCHAR);
                pstm.setNull(11, Types.VARCHAR);
            } else if ("ADMINISTRADOR".equals(tipo)) {
                pstm.setNull(9, Types.VARCHAR);
                pstm.setString(10, apoderado.getCargo());
                pstm.setNull(11, Types.VARCHAR);
            } else if ("PERSONAL_SERVICIO".equals(tipo)) {
                pstm.setNull(9, Types.VARCHAR);
                pstm.setNull(10, Types.VARCHAR);
                pstm.setString(11, apoderado.getArea());
            } else {
                pstm.setNull(9, Types.VARCHAR);
                pstm.setNull(10, Types.VARCHAR);
                pstm.setNull(11, Types.VARCHAR);
            }

            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    private Integer buscarRelacionActiva(Connection connection, int idAlumno, int idPersona) throws SQLException {
        String sql = """
        SELECT id_relacion_familiar
        FROM SIME_RELACION_FAMILIAR
        WHERE id_alumno = ?
          AND id_persona = ?
          AND activo = 1
        LIMIT 1
    """;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idAlumno);
            pstm.setInt(2, idPersona);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_relacion_familiar");
                }
            }
        }

        return null;
    }

    private int insertarRelacionFamiliar(
            Connection connection,
            int idAlumno,
            int idPersona,
            ApoderadoNuevoDTO apoderado
    ) throws SQLException {

        String sql = """
        INSERT INTO SIME_RELACION_FAMILIAR
        (
            id_alumno,
            id_persona,
            parentesco,
            contacto_emergencia,
            observaciones,
            activo
        )
        VALUES (?, ?, ?, ?, ?, 1)
    """;

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, idAlumno);
            pstm.setInt(2, idPersona);
            pstm.setString(3, normalizarParentesco(apoderado.getParentesco()));
            pstm.setBoolean(4, apoderado.isContactoEmergencia());
            pstm.setString(5, apoderado.getObservacion());

            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    private String normalizarTipoPersona(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return TipoPersona.EXTERNO.name();
        }

        return tipo.trim().toUpperCase();
    }

    private String normalizarParentesco(String parentesco) {
        if (parentesco == null || parentesco.isBlank()) {
            return "OTRO";
        }

        return parentesco
                .trim()
                .toUpperCase()
                .replace("HERMANO(A)", "HERMANO")
                .replace("TIO(A)", "TIO")
                .replace("ABUELO(A)", "ABUELO");
    }
}
