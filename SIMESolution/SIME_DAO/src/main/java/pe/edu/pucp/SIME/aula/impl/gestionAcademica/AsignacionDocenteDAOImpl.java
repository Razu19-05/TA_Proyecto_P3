package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AsignacionDocenteDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaCabeceraDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
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
    public List<ProfesorDTO> listarProfesoresPorAula(String nivel, String grado, int anio) throws SQLException {
        List<ProfesorDTO> lista = new ArrayList<>();


        String sql = """
        SELECT p.id_persona, ad.id_asignacion_docente, p.dni,
                CONCAT(p.nombres, ' ', p.apellido_paterno) AS nombre_completo, 
                p.especialidad, ad.es_tutor 
                FROM SIME_ASIGNACION_DOCENTE ad
                INNER JOIN SIME_PERSONA p ON ad.id_persona = p.id_persona
                INNER JOIN SIME_MATRICULA_CABECERA c ON ad.id_matricula_cabecera = c.id_matricula_cabecera
                INNER JOIN SIME_GRADO_SECCION gs ON c.id_grado_seccion = gs.id_grado_seccion
                INNER JOIN SIME_PERIODO_ACADEMICO pa ON c.id_periodo_academico = pa.id_periodo_academico 
                WHERE gs.nivel = ? AND gs.grado = ? AND pa.anio_escolar = ? AND ad.activo = 1 
        """;

        Connection con = TransactionContext.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nivel);
            ps.setString(2, grado);
            ps.setInt(3, anio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProfesorDTO dto = new ProfesorDTO();
                    dto.setIdPersona(rs.getInt("id_persona"));
                    dto.setIdAsignacionDocente(rs.getInt("id_asignacion_docente"));
                    dto.setDni(rs.getString("dni"));
                    dto.setNombreCompleto(rs.getString("nombre_completo"));
                    dto.setEspecialidad(rs.getString("especialidad"));
                    dto.setEsTutor(rs.getInt("es_tutor") == 1);
                    lista.add(dto);
                }
            }
        }
        return lista;
    }
}
