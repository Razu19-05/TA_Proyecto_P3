package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.PeriodoAcademicoDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;

import java.sql.*;

public class PeriodoAcademicoDAOImpl implements PeriodoAcademicoDAO {
    @Override
    public PeriodoAcademico load(Integer periodoID) throws SQLException {
        String sql = "select id_periodo_academico, anio_escolar, fecha_inicio, fecha_fin, activo " +
                "from SIME_PERIODO_ACADEMICO where id_periodo_academico = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,periodoID);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    PeriodoAcademico periodo = new PeriodoAcademico();
                    periodo.setIdPeriodoAcademico(rs.getInt("id_periodo_academico"));
                    periodo.setAnioEscolar(rs.getInt("anio_escolar"));
                    periodo.setFechaInicio(rs.getDate("fecha_inicio"));
                    periodo.setFechaFin(rs.getDate("fecha_fin"));
                    periodo.setActivo(rs.getBoolean("activo"));
                    return periodo;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public PeriodoAcademico save(PeriodoAcademico periodoAcademico) throws SQLException {
        String sql = """
                INSERT INTO SIME_PERIODO_ACADEMICO 
                (anio_escolar, fecha_inicio, fecha_fin, activo) 
                VALUES (?, ?, ?, ?)
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, periodoAcademico.getAnioEscolar());

            // Manejo seguro de fechas
            if (periodoAcademico.getFechaInicio() != null) {
                pstm.setDate(2, new java.sql.Date(periodoAcademico.getFechaInicio().getTime()));
            } else {
                pstm.setNull(2, Types.DATE);
            }

            if (periodoAcademico.getFechaFin() != null) {
                pstm.setDate(3, new java.sql.Date(periodoAcademico.getFechaFin().getTime()));
            } else {
                pstm.setNull(3, Types.DATE);
            }
            pstm.setBoolean(4, periodoAcademico.isActivo());
            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        periodoAcademico.setIdPeriodoAcademico(newId);
                    }
                }
            }
        }
        return periodoAcademico;
    }

    @Override
    public PeriodoAcademico update(PeriodoAcademico periodoAcademico) throws SQLException {
        String sql = """
                UPDATE SIME_PERIODO_ACADEMICO 
                SET anio_escolar = ?, fecha_inicio = ?, fecha_fin = ?
                WHERE id_periodo_academico = ?
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, periodoAcademico.getAnioEscolar());

            if (periodoAcademico.getFechaInicio() != null) {
                pstm.setDate(2, new java.sql.Date(periodoAcademico.getFechaInicio().getTime()));
            } else {
                pstm.setNull(2, Types.DATE);
            }

            if (periodoAcademico.getFechaFin() != null) {
                pstm.setDate(3, new java.sql.Date(periodoAcademico.getFechaFin().getTime()));
            } else {
                pstm.setNull(3, Types.DATE);
            }

            pstm.setInt(4, periodoAcademico.getIdPeriodoAcademico());

            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el perido con ID: " + periodoAcademico.getIdPeriodoAcademico());
            }
        }
        return periodoAcademico;
    }

    @Override
    public void remove(PeriodoAcademico periodoAcademico) throws SQLException {
        String sql = "UPDATE SIME_PERIODO_ACADEMICO SET activo = 0 WHERE id_periodo_academico = ?";

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, periodoAcademico.getIdPeriodoAcademico());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el perido con ID: " + periodoAcademico.getIdPeriodoAcademico());
            }
        }
    }
    @Override
    public int obtenerIdPorAnio(int anio) throws SQLException {
        String sql = "SELECT id_periodo_academico FROM SIME_PERIODO_ACADEMICO WHERE anio_escolar = ? AND activo = 1";
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, anio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_periodo_academico");
                }
            }
        }
        throw new SQLException("No se encontró un período académico activo para el año: " + anio);
    }
}
