package pe.edu.pucp.SIME.matricula.impl;

import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.matricula.DAO.PeriodoDAO;
import pe.edu.pucp.SIME.matricula.model.Periodo;

import java.sql.*;
import java.util.List;

public class PeriodoDAOImpl implements PeriodoDAO {
    @Override
    public Periodo load(Integer idPeridodo) {
        String sql = "SELECT id_periodo, anio_escolar, fecha_inicio, fecha_fin from periodo where id_periodo = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,idPeridodo);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    Periodo periodo = new Periodo();
                    periodo.setIdPeriodo(rs.getInt(1));
                    periodo.setAnioEscolar(rs.getInt(2));
                    periodo.setFechaInicio(rs.getDate(3));
                    periodo.setFechaFin(rs.getDate(4));
                    return periodo;
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public Periodo save(Periodo periodo) {
        String sql = "INSERT periodo(id_periodo,anio_escolar,fecha_inicio, fecha_fin) values (?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //se pasa la  fecha un obj sql.Date -> fecha
            pstm.setInt(1,periodo.getIdPeriodo());
            pstm.setInt(2, periodo.getAnioEscolar());
            pstm.setDate(3, new java.sql.Date(periodo.getFechaInicio().getTime()));
            pstm.setDate(4, new java.sql.Date(periodo.getFechaFin().getTime()));

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        periodo.setIdPeriodo(newId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return periodo;
    }
    @Override
    public Periodo update(Periodo periodo) {
        String sql = "UPDATE periodo SET fecha_inicio = ? WHERE anio_escolar = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setDate(1, new java.sql.Date(periodo.getFechaInicio().getTime()));
            pstm.setInt(2, periodo.getAnioEscolar());
            int resultado = pstm.executeUpdate();
            if(resultado == 0){
                System.out.println("No se encontró la matricula con ID: " + periodo.getIdPeriodo());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return periodo;
    }
    @Override
    public void remove(Periodo periodo) {

    }

    @Override
    public List<Periodo> listAll(){
        return null ;
    }
}
