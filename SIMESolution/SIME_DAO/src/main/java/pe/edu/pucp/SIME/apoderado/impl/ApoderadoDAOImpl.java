package pe.edu.pucp.SIME.apoderado.impl;

import pe.edu.pucp.SIME.apoderado.DAO.ApoderadoDAO;
import pe.edu.pucp.SIME.apoderado.model.Apoderado;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.estudiante.model.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApoderadoDAOImpl implements ApoderadoDAO {

    @Override
    public Apoderado load(Integer apoderadoID) {
        String sql = "SELECT id_apoderado, nombres, apellido_paterno, apellido_materno, dni, telefono, direccion, correo ,activo from apoderado where id_apoderado = ?";
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
                    apoderado.setActivo(rs.getInt(9));
                    return apoderado;
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Apoderado save(Apoderado apoderado) {
        String sql = "insert apoderado (nombres, apellido_paterno, apellido_materno,dni,telefono,direccion,correo) values (?,?,?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Statement.RETURN_GENERATED_KEYS permite recuperar el id que la db genero
            pstm.setString(1,apoderado.getNombres());
            pstm.setString(2,apoderado.getApellidoPaterno());
            pstm.setString(3,apoderado.getApellidoMaterno());
            pstm.setString(4,apoderado.getDni());
            pstm.setString(5,apoderado.getTelefono());
            pstm.setString(6,apoderado.getDireccion());
            pstm.setString(7,apoderado.getCorreo());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        apoderado.setIdApoderado(newId);
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return apoderado;
    }

    @Override
    public Apoderado update(Apoderado apoderado) {
        String sql ="UPDATE apoderado SET telefono = ?, correo = ? WHERE id_apoderado = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1,apoderado.getTelefono());
            pstm.setString(2,apoderado.getCorreo());
            pstm.setInt(3,apoderado.getIdApoderado());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el apoderado con ID: " + apoderado.getIdApoderado());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return apoderado;
    }

    @Override
    public void remove(Apoderado apoderado) {
        apoderado.setActivo(0);
        String sql = "UPDATE apoderado SET activo = ? WHERE id_apoderado = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,apoderado.getActivo());
            pstm.setInt(2, apoderado.getIdApoderado());
            int res = pstm.executeUpdate();
            if (res == 0){
                System.out.println("No se encontró el alumno con ID: " + apoderado.getIdApoderado());
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Apoderado>listAll(){
        List<Apoderado> apoderados = null;
        String sql = "SELECT id_apoderado, nombres, apellido_paterno, apellido_materno, dni, telefono, direccion, correo ,activo from apoderado where id_apoderado = 1";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                if(apoderados == null) apoderados = new ArrayList<>();
                Apoderado apoderado = new Apoderado();
                apoderado.setIdApoderado(rs.getInt(1));
                apoderado.setNombres(rs.getString(2));
                apoderado.setApellidoPaterno(rs.getString(3));
                apoderado.setApellidoMaterno(rs.getString(4));
                apoderado.setDni(rs.getString(5));
                apoderado.setTelefono(rs.getString(6));
                apoderado.setDireccion(rs.getString(7));
                apoderado.setCorreo(rs.getString(8));
                apoderado.setActivo(rs.getInt(9));
                apoderados.add(apoderado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return apoderados;
    }
}
