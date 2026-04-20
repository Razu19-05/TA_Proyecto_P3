package pe.edu.pucp.SIME.apoderado.impl;

import pe.edu.pucp.SIME.apoderado.DAO.ApoderadoDAO;
import pe.edu.pucp.SIME.apoderado.model.Apoderado;
import pe.edu.pucp.SIME.configuracion.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApoderadoDAOImpl implements ApoderadoDAO {

    @Override
    public Apoderado load(Integer apoderadoID) {
        String sql = "SELECT id_apoderado, nombres, apellido_paterno, apellido_materno, dni, telefono, direccion, correo from apoderado where id_apoderado = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,apoderadoID);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    Apoderado apoderado = new Apoderado();
                    apoderado.setIdApoderado(rs.getInt(1));
                    apoderado.setNombres(rs.getString(2));
                    apoderado.setApellidoPaterno(rs.getString(3));
                    apoderado.setApellidoMaterno(rs.getString(4));
                    apoderado.setDni(rs.getString(5));
                    apoderado.setTelefono(rs.getString(6));
                    apoderado.setDireccion(rs.getString(7));
                    apoderado.setCorreo(rs.getString(8));
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Apoderado save(Apoderado apoderado) {
        String sql = "INSERT Apoderado(nombres, apellido_paterno, apellido_materno,dni,telefono,direccion,correo) values (?,?,?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){

            pstm.setString(1,apoderado.getNombres());
            pstm.setString(2,apoderado.getApellidoPaterno());
            pstm.setString(3,apoderado.getApellidoMaterno());
            pstm.setString(4,apoderado.getDni());
            pstm.setString(5,apoderado.getTelefono());
            pstm.setString(6,apoderado.getDireccion());
            pstm.setString(7,apoderado.getCorreo());
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return apoderado;
    }

    @Override
    public Apoderado update(Apoderado apoderado) {
        return null;
    }

    @Override
    public void remove(Apoderado apoderado) {

    }
}
