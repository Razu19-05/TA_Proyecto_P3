package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AsignacionDocenteDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaCabeceraDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.AsignacionDocenteRequestDTO;
import pe.edu.pucp.SIME.model.DTO.ProfesorAulaDTO;
import pe.edu.pucp.SIME.model.DTO.ProfesorDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.AsignacionDocente;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDocenteDAOImpl implements AsignacionDocenteDAO {

    public Persona buscarPersona(int id) throws SQLException{
        PersonaDAO personaDAO = new PersonaDAOImpl();
        Persona persona = personaDAO.load(id);
        return persona;
    }
    public MatriculaCabecera buscarMatriculaCabecera (int id) throws SQLException{
        MatriculaCabeceraDAO matriculaCabeceraDAO = new MatriculaCabeceraDAOImpl();
        MatriculaCabecera matriculaCabecera = matriculaCabeceraDAO.load(id);
        return matriculaCabecera;
    }

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
                    Persona persona = buscarPersona(rs.getInt("id_persona"));
                    asignacion.setPersona(persona);

                    // Cascarón para la Matrícula
                    MatriculaCabecera matricula = buscarMatriculaCabecera(rs.getInt("id_matricula"));
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



    @Override
    public List<ProfesorAulaDTO> listarPorAula(int idAula) throws SQLException {
        List<ProfesorAulaDTO> lista = new ArrayList<>();

        String sql = """
            SELECT
                ad.id_asignacion_docente,
                ad.id_matricula_cabecera,
                p.id_persona,
                p.dni,
                CONCAT(p.nombres, ' ', p.apellido_paterno, ' ', p.apellido_materno) AS nombres_completos,
                p.tipo,
                p.especialidad,
                p.cargo,
                p.area,
                p.telefono,
                p.correo,
                ad.es_tutor,
                ad.observacion,
                CASE
                    WHEN ad.activo = 1 THEN 'Activo'
                    ELSE 'Inactivo'
                END AS estado
            FROM SIME_ASIGNACION_DOCENTE ad
            INNER JOIN SIME_PERSONA p
                ON p.id_persona = ad.id_persona
            INNER JOIN SIME_MATRICULA_CABECERA mc
                ON mc.id_matricula_cabecera = ad.id_matricula_cabecera
            WHERE mc.id_aula = ?
              AND mc.activo = 1
              AND ad.activo = 1
              AND p.activo = 1
            ORDER BY ad.es_tutor DESC, p.apellido_paterno, p.apellido_materno, p.nombres
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idAula);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProfesorAulaDTO(rs));
                }
            }
        }

        return lista;
    }

    @Override
    public List<ProfesorAulaDTO> listarPorMatriculaCabecera(int idMatriculaCabecera) throws SQLException {
        List<ProfesorAulaDTO> lista = new ArrayList<>();

        String sql = """
            SELECT
                ad.id_asignacion_docente,
                ad.id_matricula_cabecera,
                p.id_persona,
                p.dni,
                CONCAT(p.nombres, ' ', p.apellido_paterno, ' ', p.apellido_materno) AS nombres_completos,
                p.tipo,
                p.especialidad,
                p.cargo,
                p.area,
                p.telefono,
                p.correo,
                ad.es_tutor,
                ad.observacion,
                CASE
                    WHEN ad.activo = 1 THEN 'Activo'
                    ELSE 'Inactivo'
                END AS estado
            FROM SIME_ASIGNACION_DOCENTE ad
            INNER JOIN SIME_PERSONA p
                ON p.id_persona = ad.id_persona
            WHERE ad.id_matricula_cabecera = ?
              AND ad.activo = 1
              AND p.activo = 1
            ORDER BY ad.es_tutor DESC, p.apellido_paterno, p.apellido_materno, p.nombres
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idMatriculaCabecera);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProfesorAulaDTO(rs));
                }
            }
        }

        return lista;
    }

    @Override
    public List<ProfesorAulaDTO> listarProfesoresDisponibles(String criterio) throws SQLException {
        List<ProfesorAulaDTO> lista = new ArrayList<>();

        String sql = """
            SELECT
                0 AS id_asignacion_docente,
                NULL AS id_matricula_cabecera,
                p.id_persona,
                p.dni,
                CONCAT(p.nombres, ' ', p.apellido_paterno, ' ', p.apellido_materno) AS nombres_completos,
                p.tipo,
                p.especialidad,
                p.cargo,
                p.area,
                p.telefono,
                p.correo,
                0 AS es_tutor,
                '' AS observacion,
                CASE
                    WHEN p.activo = 1 THEN 'Activo'
                    ELSE 'Inactivo'
                END AS estado
            FROM SIME_PERSONA p
            WHERE p.activo = 1
              AND p.tipo = 'PROFESOR'
              AND (
                    ? IS NULL
                    OR p.dni LIKE ?
                    OR UPPER(p.nombres) LIKE UPPER(?)
                    OR UPPER(p.apellido_paterno) LIKE UPPER(?)
                    OR UPPER(p.apellido_materno) LIKE UPPER(?)
                    OR UPPER(CONCAT(p.nombres, ' ', p.apellido_paterno, ' ', p.apellido_materno)) LIKE UPPER(?)
                  )
            ORDER BY p.apellido_paterno, p.apellido_materno, p.nombres
        """;

        Connection connection = TransactionContext.getConnection();

        String filtro = normalizarFiltro(criterio);
        String filtroLike = filtro == null ? null : "%" + filtro + "%";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, filtro);
            pstm.setString(2, filtroLike);
            pstm.setString(3, filtroLike);
            pstm.setString(4, filtroLike);
            pstm.setString(5, filtroLike);
            pstm.setString(6, filtroLike);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProfesorAulaDTO(rs));
                }
            }
        }

        return lista;
    }

    @Override
    public int obtenerMatriculaCabeceraActivaPorAula(int idAula) throws SQLException {
        String sql = """
            SELECT mc.id_matricula_cabecera
            FROM SIME_MATRICULA_CABECERA mc
            INNER JOIN SIME_PERIODO_ACADEMICO pa
                ON pa.id_periodo_academico = mc.id_periodo_academico
            WHERE mc.id_aula = ?
              AND mc.activo = 1
            ORDER BY pa.anio_escolar DESC, mc.id_matricula_cabecera DESC
            LIMIT 1
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idAula);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_matricula_cabecera");
                }
            }
        }

        return 0;
    }

    @Override
    public boolean existeAsignacionActiva(int idPersona, int idMatriculaCabecera, int idIgnorar) throws SQLException {
        String sql = """
            SELECT COUNT(*) AS total
            FROM SIME_ASIGNACION_DOCENTE
            WHERE id_persona = ?
              AND id_matricula_cabecera = ?
              AND activo = 1
              AND id_asignacion_docente <> ?
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idPersona);
            pstm.setInt(2, idMatriculaCabecera);
            pstm.setInt(3, idIgnorar);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }

        return false;
    }

    @Override
    public boolean existeTutorActivo(int idMatriculaCabecera, int idIgnorar) throws SQLException {
        String sql = """
            SELECT COUNT(*) AS total
            FROM SIME_ASIGNACION_DOCENTE
            WHERE id_matricula_cabecera = ?
              AND es_tutor = 1
              AND activo = 1
              AND id_asignacion_docente <> ?
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idMatriculaCabecera);
            pstm.setInt(2, idIgnorar);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }

        return false;
    }

    @Override
    public int asignarDocente(AsignacionDocenteRequestDTO request) throws SQLException {
        String sql = """
            INSERT INTO SIME_ASIGNACION_DOCENTE
            (id_persona, id_matricula_cabecera, es_tutor, observacion, activo)
            VALUES (?, ?, ?, ?, 1)
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, request.getIdPersona());
            pstm.setInt(2, request.getIdMatriculaCabecera());
            pstm.setBoolean(3, request.isEsTutor());
            pstm.setString(4, request.getObservacion());

            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    @Override
    public boolean actualizarAsignacion(int idAsignacionDocente, AsignacionDocenteRequestDTO request) throws SQLException {
        String sql = """
            UPDATE SIME_ASIGNACION_DOCENTE
            SET id_persona = ?,
                id_matricula_cabecera = ?,
                es_tutor = ?,
                observacion = ?
            WHERE id_asignacion_docente = ?
              AND activo = 1
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, request.getIdPersona());
            pstm.setInt(2, request.getIdMatriculaCabecera());
            pstm.setBoolean(3, request.isEsTutor());
            pstm.setString(4, request.getObservacion());
            pstm.setInt(5, idAsignacionDocente);

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarAsignacion(int idAsignacionDocente) throws SQLException {
        String sql = """
            UPDATE SIME_ASIGNACION_DOCENTE
            SET activo = 0
            WHERE id_asignacion_docente = ?
        """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idAsignacionDocente);
            return pstm.executeUpdate() > 0;
        }
    }

    private ProfesorAulaDTO mapearProfesorAulaDTO(ResultSet rs) throws SQLException {
        ProfesorAulaDTO dto = new ProfesorAulaDTO();

        dto.setIdAsignacionDocente(rs.getInt("id_asignacion_docente"));

        int idMatriculaCabecera = rs.getInt("id_matricula_cabecera");
        dto.setIdMatriculaCabecera(rs.wasNull() ? null : idMatriculaCabecera);

        dto.setIdPersona(rs.getInt("id_persona"));
        dto.setDni(rs.getString("dni"));
        dto.setNombresCompletos(rs.getString("nombres_completos"));
        dto.setTipo(rs.getString("tipo"));
        dto.setEspecialidad(rs.getString("especialidad"));
        dto.setCargo(rs.getString("cargo"));
        dto.setArea(rs.getString("area"));
        dto.setTelefono(rs.getString("telefono"));
        dto.setCorreo(rs.getString("correo"));
        dto.setEsTutor(rs.getBoolean("es_tutor"));
        dto.setObservacion(rs.getString("observacion"));
        dto.setEstado(rs.getString("estado"));

        return dto;
    }

    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank() || valor.equalsIgnoreCase("Todos")) {
            return null;
        }

        return valor.trim();
    }
}
