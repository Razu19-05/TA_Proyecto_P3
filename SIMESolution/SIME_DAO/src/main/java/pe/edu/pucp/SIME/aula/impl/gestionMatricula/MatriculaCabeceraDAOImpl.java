package pe.edu.pucp.SIME.aula.impl.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AulaDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.GradoSeccionDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.PeriodoAcademicoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.AulaDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.GradoSeccionDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.PeriodoAcademicoDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

import java.sql.*;

public class MatriculaCabeceraDAOImpl implements MatriculaCabeceraDAO {

    public PeriodoAcademico buscarPeriodo (int id) throws SQLException{
        PeriodoAcademicoDAO periodoAcademicoDAO = new PeriodoAcademicoDAOImpl();
        PeriodoAcademico periodoAcademico = periodoAcademicoDAO.load(id);
        return periodoAcademico;
    }

    public GradoSeccion buscarGrado (int id) throws SQLException{
        GradoSeccionDAO gradoSeccionDAO = new GradoSeccionDAOImpl();
        GradoSeccion gradoSeccion = gradoSeccionDAO.load(id);
        return gradoSeccion;
    }

    public Aula buscarAula (int id) throws SQLException{
        AulaDAO aulaDAO = new AulaDAOImpl();
        Aula aula = aulaDAO.load(id);
        return aula;
    }

    @Override
    public MatriculaCabecera load(Integer id) throws SQLException {
        String sql = "select id_matricula_cabecera, id_periodo_academico, id_grado_seccion, id_aula, " +
                "fecha_inicio_matricula, fecha_fin_matricula, total_vacantes, vacantes_ocupadas, activo " +
                "from SIME_MATRICULA_CABECERA where id_matricula_cabecera = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    MatriculaCabecera matriculaCabecera = new MatriculaCabecera();
                    PeriodoAcademico periodo = buscarPeriodo(rs.getInt("id_periodo_academico"));
                    GradoSeccion grado = buscarGrado(rs.getInt("id_grado_seccion"));
                    Aula aula = buscarAula(rs.getInt("id_aula"));
                    matriculaCabecera.setPeriodoAcademico(periodo);
                    matriculaCabecera.setGradoSeccion(grado);
                    matriculaCabecera.setAula(aula);
                    matriculaCabecera.setIdMatriculaCabecera(rs.getInt("id_matricula_cabecera"));
                    matriculaCabecera.setFechaInicioMatricula(rs.getDate("fecha_inicio_matricula"));
                    matriculaCabecera.setFechaFinMatricula(rs.getDate("fecha_fin_matricula"));
                    matriculaCabecera.setTotalVacantes(rs.getInt("total_vacantes"));
                    matriculaCabecera.setVacantesOcupadas(rs.getInt("vacantes_ocupadas"));
                    matriculaCabecera.setActivo(rs.getBoolean("activo"));
                    return matriculaCabecera;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MatriculaCabecera obtenerPorGradoSeccionActivo(int idGradoSeccion) throws SQLException {
        String sql = "select id_matricula_cabecera, id_periodo_academico, id_grado_seccion, id_aula, " +
                "fecha_inicio_matricula, fecha_fin_matricula, total_vacantes, vacantes_ocupadas, activo " +
                "from SIME_MATRICULA_CABECERA where id_grado_seccion = ? AND activo = 1 ";

        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idGradoSeccion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MatriculaCabecera matriculaCabecera = new MatriculaCabecera();
                    PeriodoAcademico periodo = buscarPeriodo(rs.getInt("id_periodo_academico"));
                    GradoSeccion grado = buscarGrado(rs.getInt("id_grado_seccion"));
                    Aula aula = buscarAula(rs.getInt("id_aula"));
                    matriculaCabecera.setPeriodoAcademico(periodo);
                    matriculaCabecera.setGradoSeccion(grado);
                    matriculaCabecera.setAula(aula);
                    matriculaCabecera.setIdMatriculaCabecera(rs.getInt("id_matricula_cabecera"));
                    matriculaCabecera.setFechaInicioMatricula(rs.getDate("fecha_inicio_matricula"));
                    matriculaCabecera.setFechaFinMatricula(rs.getDate("fecha_fin_matricula"));
                    matriculaCabecera.setTotalVacantes(rs.getInt("total_vacantes"));
                    matriculaCabecera.setVacantesOcupadas(rs.getInt("vacantes_ocupadas"));
                    matriculaCabecera.setActivo(rs.getBoolean("activo"));
                    return matriculaCabecera;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MatriculaCabecera save(MatriculaCabecera matricula) throws SQLException {
        String sql = """
                INSERT INTO SIME_MATRICULA_CABECERA 
                (id_periodo_academico, id_grado_seccion, id_aula, fecha_inicio_matricula, 
                 fecha_fin_matricula, total_vacantes, vacantes_ocupadas, activo) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Extraer IDs de los objetos foráneos
            pstm.setInt(1, matricula.getPeriodoAcademico().getIdPeriodoAcademico());
            pstm.setInt(2, matricula.getGradoSeccion().getIdGradoSeccion());
            pstm.setInt(3, matricula.getAula().getIdAula());

            // Manejo seguro de fechas
            if (matricula.getFechaInicioMatricula() != null) {
                pstm.setDate(4, new java.sql.Date(matricula.getFechaInicioMatricula().getTime()));
            } else {
                pstm.setNull(4, java.sql.Types.DATE);
            }

            if (matricula.getFechaFinMatricula() != null) {
                pstm.setDate(5, new java.sql.Date(matricula.getFechaFinMatricula().getTime()));
            } else {
                pstm.setNull(5, java.sql.Types.DATE);
            }

            pstm.setInt(6, matricula.getTotalVacantes());
            pstm.setInt(7, matricula.getVacantesOcupadas());
            pstm.setBoolean(8, matricula.isActivo());

            int affectedRows = pstm.executeUpdate();

            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        matricula.setIdMatriculaCabecera(newId);
                    }
                }
            }
        }
        return matricula;
    }

    @Override
    public MatriculaCabecera update(MatriculaCabecera matricula) throws SQLException {
        String sql = """
                UPDATE SIME_MATRICULA_CABECERA 
                SET id_periodo_academico = ?, id_grado_seccion = ?, id_aula = ?, 
                    fecha_inicio_matricula = ?, fecha_fin_matricula = ?, 
                    total_vacantes = ?, vacantes_ocupadas = ?, activo = ? 
                WHERE id_matricula_cabecera = ?
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, matricula.getPeriodoAcademico().getIdPeriodoAcademico());
            pstm.setInt(2, matricula.getGradoSeccion().getIdGradoSeccion());
            pstm.setInt(3, matricula.getAula().getIdAula());

            if (matricula.getFechaInicioMatricula() != null) {
                pstm.setDate(4, new java.sql.Date(matricula.getFechaInicioMatricula().getTime()));
            } else {
                pstm.setNull(4, java.sql.Types.DATE);
            }

            if (matricula.getFechaFinMatricula() != null) {
                pstm.setDate(5, new java.sql.Date(matricula.getFechaFinMatricula().getTime()));
            } else {
                pstm.setNull(5, java.sql.Types.DATE);
            }

            pstm.setInt(6, matricula.getTotalVacantes());
            pstm.setInt(7, matricula.getVacantesOcupadas());
            pstm.setBoolean(8, matricula.isActivo());

            // Parámetro del WHERE
            pstm.setInt(9, matricula.getIdMatriculaCabecera());

            int affectedRows = pstm.executeUpdate();

            if(affectedRows == 0){
                System.out.println("No se encontró el concepto de MatriculaCabecera con ID: " + matricula.getIdMatriculaCabecera());
            }
        }
        return matricula;
    }

    @Override
    public void remove(MatriculaCabecera matriculaCabecera) throws SQLException {
        String sql = "UPDATE SIME_MATRICULA_CABECERA SET activo = 0 WHERE id_matricula_cabecera = ?";

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, matriculaCabecera.getIdMatriculaCabecera());
            int affectedRows = pstm.executeUpdate();

            if(affectedRows == 0){
                System.out.println("No se encontró el concepto de MatriculaCabecera con ID: " + matriculaCabecera.getIdMatriculaCabecera());
            }
        }
    }


}
