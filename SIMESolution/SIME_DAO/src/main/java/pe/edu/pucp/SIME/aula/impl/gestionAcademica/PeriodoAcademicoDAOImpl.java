package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.PeriodoAcademicoDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;

import java.sql.*;

public class PeriodoAcademicoDAOImpl implements PeriodoAcademicoDAO {
    @Override
    public PeriodoAcademico load(Integer periodoID) throws SQLException {
        String sql = """
                SELECT id_periodo_academico,
                anio_escolar,
                fecha_inicio,
                fecha_fin,
                activo FROM SIME_PERIODO_ACADEMICO WHERE id_periodo_academico = ?
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,periodoID);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    PeriodoAcademico perido = new PeriodoAcademico();
                    perido.setIdPeriodoAcademico(rs.getInt(1));
                    perido.setAnioEscolar(rs.getInt(2));
                    perido.setFechaInicio(rs.getDate(3));
                    perido.setFechaFin(rs.getDate(4));
                    perido.setActivo(rs.getBoolean(5));
                    return perido;
                }
            }
            return null;
        }
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
}
