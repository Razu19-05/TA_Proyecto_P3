package pe.edu.pucp.SIME.estudiante.impl;

import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.estudiante.DAO.AlumnoDAO;
import pe.edu.pucp.SIME.estudiante.model.Alumno;

import java.sql.*;

public class AlumnoDAOImpl implements AlumnoDAO  {
    @Override
    public Alumno load(Integer alumnoID) {
        String sql = "select id_alumno, nombres, apellido_paterno, apellido_materno, direccion, telefono, correo from alumno where id_alumno = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, alumnoID);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(rs.getInt(1));
                    alumno.setNombres(rs.getString(2));
                    alumno.setApellidoPaterno(rs.getString(3));
                    alumno.setApellidoMaterno(rs.getString(4));
                    alumno.setDireccion(rs.getString(5));
                    alumno.setTelefono(rs.getString(6));
                    alumno.setCorreo(rs.getString(7));
                    return alumno;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Alumno save(Alumno alumno) {
        String sql = "insert alumno (nombres, apellido_paterno, apellido_materno, direccion,telefono,correo) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, alumno.getNombres());
            pstm.setString(2, alumno.getApellidoPaterno());
            pstm.setString(3, alumno.getApellidoMaterno());
            pstm.setString(4, alumno.getDireccion());
            pstm.setString(5, alumno.getTelefono());
            pstm.setString(6, alumno.getCorreo());

            //id_alumno
            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        alumno.setIdAlumno(newId);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alumno;
    }
    @Override
    public Alumno update(Alumno alumno) {
        String sql = "UPDATE alumno SET telefono = ? WHERE id_alumno = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1,alumno.getTelefono());
            pstm.setInt(2,alumno.getIdAlumno());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el alumno con ID: " + alumno.getIdAlumno());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alumno;
    }
    @Override
    public void remove(Alumno alumno) {
        String sql = "UPDATE alumno SET estado = 0 WHERE id_alumno = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,alumno.getIdAlumno());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el alumno con ID: " + alumno.getIdAlumno());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
