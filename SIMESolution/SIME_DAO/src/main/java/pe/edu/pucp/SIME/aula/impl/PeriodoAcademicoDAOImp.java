package pe.edu.pucp.SIME.aula.impl;

import pe.edu.pucp.SIME.aula.DAO.PeriodoAcademicoDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.model.PeriodoAcademico;

import java.sql.*;
import java.util.List;

public class PeriodoAcademicoDAOImp implements PeriodoAcademicoDAO {
    @Override
    public PeriodoAcademico load(Integer idPeriodoAcademico) {
        String sql = "select id_periodo_academico, anio_escolar, fecha_inicio, fecha_fin, activo from SIME_PERIODO_ACADEMICO where id_periodo_academico = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,idPeriodoAcademico);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    PeriodoAcademico periodo = new PeriodoAcademico();
                    periodo.setIdPeriodoAcademico(rs.getInt(1));
                    periodo.setAnioEscolar(rs.getInt(2));
                    periodo.setFechaInicio(rs.getDate(3));
                    periodo.setFechaFin(rs.getDate(4));
                    periodo.setActivo(rs.getInt(5));
                    return periodo;
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public PeriodoAcademico save(PeriodoAcademico periodoAcademico) {
        String sql = "insert into SIME_PERIODO_ACADEMICO (anio_escolar,fecha_inicio,fecha_fin,activo) VALUES (?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Statement.RETURN_GENERATED_KEYS permite recuperar el id que la db genero
            pstm.setInt(1,periodoAcademico.getAnioEscolar());
            pstm.setDate(2,new java.sql.Date(periodoAcademico.getFechaInicio().getTime()));
            pstm.setDate(3,new java.sql.Date(periodoAcademico.getFechaFin().getTime()));
            pstm.setInt(4,periodoAcademico.getActivo());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        periodoAcademico.setIdPeriodoAcademico(newId);
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return periodoAcademico;
    }

    @Override
    public PeriodoAcademico update(PeriodoAcademico periodoAcademico) {
        String sql ="UPDATE SIME_PERIODO_ACADEMICO SET fecha_fin = ? WHERE id_periodo_academico = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setDate(1, new java.sql.Date(periodoAcademico.getFechaFin().getTime()));
            pstm.setInt(2,periodoAcademico.getIdPeriodoAcademico());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el apoderado con ID: " + periodoAcademico.getIdPeriodoAcademico());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return periodoAcademico;
    }

    @Override
    public void remove(PeriodoAcademico periodoAcademico) {
        String sql = "UPDATE SIME_PERIODO_ACADEMICO SET activo = 0 WHERE id_periodo_academico = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,periodoAcademico.getIdPeriodoAcademico());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el alumno con ID: " + periodoAcademico.getIdPeriodoAcademico());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PeriodoAcademico> listAll() {
        return List.of();
    }
}
