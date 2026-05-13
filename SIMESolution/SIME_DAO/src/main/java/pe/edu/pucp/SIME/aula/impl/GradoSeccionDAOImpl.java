package pe.edu.pucp.SIME.aula.impl;

import pe.edu.pucp.SIME.aula.DAO.GradoSeccionDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.model.Aula;
import pe.edu.pucp.SIME.model.GradoSeccion;
import pe.edu.pucp.SIME.model.TipoAula;

import java.sql.*;
import java.util.List;

public class GradoSeccionDAOImpl implements GradoSeccionDAO {

    @Override
    public GradoSeccion load(Integer idGradoSeccion) {
        String sql = "select id_grado_seccion, grado, seccion, vacantes_maximas, activo from SIME_GRADO_SECCION where id_grado_seccion = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,idGradoSeccion);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    GradoSeccion grado = new GradoSeccion();
                    grado.setIdGradoSeccion(rs.getInt(1));
                    grado.setGrado(rs.getString(2));
                    grado.setSeccion(rs.getString(3));
                    grado.setVacantesMaximas(rs.getInt(4));
                    grado.setActivo(rs.getInt(5));
                    return grado;
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public GradoSeccion save(GradoSeccion gradoSeccion) {
        String sql = "insert into SIME_GRADO_SECCION (grado,seccion,vacantes_maximas,activo) VALUES (?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Statement.RETURN_GENERATED_KEYS permite recuperar el id que la db genero
            pstm.setString(1,gradoSeccion.getGrado());
            pstm.setString(2,gradoSeccion.getSeccion());
            pstm.setInt(3,gradoSeccion.getVacantesMaximas());
            pstm.setInt(4,gradoSeccion.getActivo());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        gradoSeccion.setIdGradoSeccion(newId);
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return gradoSeccion;
    }

    @Override
    public GradoSeccion update(GradoSeccion gradoSeccion) {
        String sql ="UPDATE SIME_GRADO_SECCION SET vacantes_maximas = ? WHERE id_grado_seccion = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,gradoSeccion.getVacantesMaximas());
            pstm.setInt(2,gradoSeccion.getIdGradoSeccion());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el apoderado con ID: " + gradoSeccion.getIdGradoSeccion());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gradoSeccion;
    }

    @Override
    public void remove(GradoSeccion gradoSeccion) {
        String sql = "UPDATE SIME_GRADO_SECCION SET activo = 0 WHERE id_grado_seccion = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,gradoSeccion.getIdGradoSeccion());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el alumno con ID: " + gradoSeccion.getIdGradoSeccion());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GradoSeccion> listAll() {
        return List.of();
    }
}
