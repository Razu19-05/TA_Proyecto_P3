package pe.edu.pucp.SIME.aula.impl.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaDetalleDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.AlumnoDAOImpl;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;
import pe.edu.pucp.SIME.model.gestionMatricula.TipoMatricula;
import pe.edu.pucp.SIME.model.DTO.HistorialMatriculaDTO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDetalleDAOImpl implements MatriculaDetalleDAO {
    public MatriculaCabecera buscarMatriculaCabecera (int id) throws SQLException{
        MatriculaCabeceraDAO matriculaCabeceraDAO = new MatriculaCabeceraDAOImpl();
        MatriculaCabecera matriculaCabecera = matriculaCabeceraDAO.load(id);
        return matriculaCabecera;
    }

    public Alumno buscarAlumno (int id) throws SQLException{
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
        Alumno alumno = alumnoDAO.load(id);
        return alumno;
    }

    @Override
    public MatriculaDetalle load(Integer id) throws SQLException {
        String sql = "select id_matricula_detalle, id_matricula_cabecera, id_alumno, fecha_matricula, estado, activo " +
                "from SIME_MATRICULA_DETALLE where id_matricula_detalle = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    MatriculaDetalle matricula = new MatriculaDetalle();
                    matricula.setIdMatriculaDetalle(rs.getInt("id_matricula_detalle"));
                    MatriculaCabecera matriculaCabecera = buscarMatriculaCabecera(rs.getInt("id_matricula_cabecera"));
                    Alumno alumno = buscarAlumno(rs.getInt("id_alumno"));
                    matricula.setMatriculaCabecera(matriculaCabecera);
                    matricula.setAlumno(alumno);
                    matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                    String estado = rs.getString("estado");
                    matricula.setEstado(TipoMatricula.valueOf(estado));
                    matricula.setActivo(rs.getBoolean("activo"));
                    return matricula;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
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
        try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public MatriculaDetalle obtenerPorAlumno(int idAlumno) throws SQLException {
        String sql = "select id_matricula_detalle, id_matricula_cabecera, id_alumno, fecha_matricula, estado, activo " +
                "from SIME_MATRICULA_DETALLE where id_alumno = ? and activo = 1";

        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,idAlumno);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    MatriculaDetalle matricula = new MatriculaDetalle();
                    matricula.setIdMatriculaDetalle(rs.getInt("id_matricula_detalle"));
                    MatriculaCabecera matriculaCabecera = buscarMatriculaCabecera(rs.getInt("id_matricula_cabecera"));
                    Alumno alumno = buscarAlumno(rs.getInt("id_alumno"));
                    matricula.setMatriculaCabecera(matriculaCabecera);
                    matricula.setAlumno(alumno);
                    matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                    String estado = rs.getString("estado");
                    matricula.setEstado(TipoMatricula.valueOf(estado));
                    matricula.setActivo(rs.getBoolean("activo"));
                    return matricula;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<MatriculaDetalle> listarMatriculasPorAlumno(int idAlumno) throws SQLException {
        String sql = "select id_matricula_detalle, id_matricula_cabecera, id_alumno, fecha_matricula, estado, activo " +
                "from SIME_MATRICULA_DETALLE where id_alumno = ?";

        List<MatriculaDetalle> matriculas = new ArrayList<>();
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,idAlumno);
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    MatriculaDetalle matricula = new MatriculaDetalle();
                    matricula.setIdMatriculaDetalle(rs.getInt("id_matricula_detalle"));
                    MatriculaCabecera matriculaCabecera = buscarMatriculaCabecera(rs.getInt("id_matricula_cabecera"));
                    Alumno alumno = buscarAlumno(rs.getInt("id_alumno"));
                    matricula.setMatriculaCabecera(matriculaCabecera);
                    matricula.setAlumno(alumno);
                    matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                    String estado = rs.getString("estado");
                    matricula.setEstado(TipoMatricula.valueOf(estado));
                    matricula.setActivo(rs.getBoolean("activo"));
                    matriculas.add(matricula);

                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return matriculas;
    }

    @Override
    public boolean existeMatriculaActiva(int idAlumno, int idMatriculaCabecera) throws SQLException {

        String sql = """
        SELECT COUNT(*) AS total
        FROM SIME_MATRICULA_DETALLE
        WHERE id_alumno = ?
          AND id_matricula_cabecera = ?
          AND activo = 1
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idAlumno);
            pstm.setInt(2, idMatriculaCabecera);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }

        return false;
    }
    @Override
    public int insertarMatriculaAlumnoNuevo(int idAlumno, int idMatriculaCabecera) throws SQLException {

        String sql = """
        INSERT INTO SIME_MATRICULA_DETALLE
        (
            id_alumno,
            id_matricula_cabecera,
            fecha_matricula,
            estado,
            activo
        )
        VALUES (?, ?, CURDATE(), 'MATRICULADO', 1)
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, idAlumno);
            pstm.setInt(2, idMatriculaCabecera);

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
    public List<HistorialMatriculaDTO> listarHistorialMatriculas() throws SQLException {
        List<HistorialMatriculaDTO> historial = new ArrayList<>();

        String sql = """
        SELECT
            md.id_matricula_detalle,
            al.id_alumno,
            al.dni,
            CONCAT(al.nombres, ' ', al.apellido_paterno, ' ', al.apellido_materno) AS nombres_completos,
            CAST(pa.anio_escolar AS CHAR) AS periodo,

            CASE
                WHEN gs.nivel = 'INICIAL' THEN 'Inicial'
                WHEN gs.nivel = 'PRIMARIA' THEN 'Primaria'
                WHEN gs.nivel = 'SECUNDARIA' THEN 'Secundaria'
                ELSE gs.nivel
            END AS nivel,

            gs.grado,
            au.codigo AS aula,
            md.fecha_matricula,
            md.estado,
            md.activo

        FROM SIME_MATRICULA_DETALLE md

        INNER JOIN SIME_ALUMNO al
            ON al.id_alumno = md.id_alumno

        INNER JOIN SIME_MATRICULA_CABECERA mc
            ON mc.id_matricula_cabecera = md.id_matricula_cabecera

        INNER JOIN SIME_PERIODO_ACADEMICO pa
            ON pa.id_periodo_academico = mc.id_periodo_academico

        INNER JOIN SIME_GRADO_SECCION gs
            ON gs.id_grado_seccion = mc.id_grado_seccion

        INNER JOIN SIME_AULA au
            ON au.id_aula = mc.id_aula

        WHERE md.activo = 1
          AND al.activo = 1

        ORDER BY
            md.fecha_matricula DESC,
            pa.anio_escolar DESC,
            gs.nivel,
            gs.grado,
            al.apellido_paterno,
            al.apellido_materno,
            al.nombres
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                HistorialMatriculaDTO dto = new HistorialMatriculaDTO();

                dto.setIdMatriculaDetalle(rs.getInt("id_matricula_detalle"));
                dto.setIdAlumno(rs.getInt("id_alumno"));
                dto.setDni(rs.getString("dni"));
                dto.setNombresCompletos(rs.getString("nombres_completos"));
                dto.setPeriodo(rs.getString("periodo"));
                dto.setNivel(rs.getString("nivel"));
                dto.setGrado(rs.getString("grado"));
                dto.setAula(rs.getString("aula"));
                dto.setFechaMatricula(rs.getDate("fecha_matricula"));
                dto.setEstado(rs.getString("estado"));
                dto.setActivo(rs.getBoolean("activo"));

                historial.add(dto);
            }
        }

        return historial;
    }

}
