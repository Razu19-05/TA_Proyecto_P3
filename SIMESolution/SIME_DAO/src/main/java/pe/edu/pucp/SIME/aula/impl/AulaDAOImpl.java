package pe.edu.pucp.SIME.aula.impl;

import pe.edu.pucp.SIME.aula.DAO.AulaDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.model.Aula;
import pe.edu.pucp.SIME.model.TipoAula;

import java.sql.*;
import java.util.List;

public class AulaDAOImpl implements AulaDAO {
    @Override
    public Aula load(Integer idAula) {
        String sql = "select id_aula, codigo, tipo_aula, capacidad, activo from SIME_AULA where id_aula = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,idAula);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    Aula aula = new Aula();
                    String tipoStr = rs.getString(3);
                    aula.setIdAula(rs.getInt(1));
                    aula.setCodigo(rs.getString(2));
                    if(tipoStr != null){
                        aula.setTipoAula(TipoAula.valueOf(tipoStr));
                    }
                    aula.setCapacidad(rs.getInt(4));
                    aula.setActivo(rs.getInt(5));
                    return aula;
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Aula save(Aula aula) {
        String sql = "insert into SIME_AULA (codigo,tipo_aula,capacidad,activo) VALUES (?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Statement.RETURN_GENERATED_KEYS permite recuperar el id que la db genero
            pstm.setString(1,aula.getCodigo());
            pstm.setString(2,aula.getTipoAula()!= null ? aula.getTipoAula().name() : null);
            pstm.setInt(3,aula.getCapacidad());
            pstm.setInt(4,aula.getActivo());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        aula.setIdAula(newId);
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return aula;
    }

    @Override
    public Aula update(Aula aula) {
        String sql ="UPDATE SIME_AULA SET capacidad = ? WHERE id_aula = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,aula.getCapacidad());
            pstm.setInt(2,aula.getIdAula());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el apoderado con ID: " + aula.getIdAula());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aula;
    }

    @Override
    public void remove(Aula aula) {
        String sql = "UPDATE SIME_AULA SET activo = 0 WHERE id_aula = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,aula.getIdAula());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el alumno con ID: " + aula.getIdAula());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> listAll() {
        return List.of();
    }
}
