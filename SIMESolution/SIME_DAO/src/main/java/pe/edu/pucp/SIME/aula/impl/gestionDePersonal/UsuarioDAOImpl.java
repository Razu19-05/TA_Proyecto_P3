package pe.edu.pucp.SIME.aula.impl.gestionDePersonal;

import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.UsuarioDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.model.gestionDePersonal.TipoUsuario;
import pe.edu.pucp.SIME.model.gestionDePersonal.Usuario;

import java.sql.*;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public Usuario load(Integer usuarioId) throws SQLException {
        String sql = """
                select
                id_usuario,
                nombre_usuario,
                correo,
                contrasena,                
                rol,
                activo,
                where id_usuario = ?              
                """;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, usuarioId);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt(2));
                    usuario.setNombreUsuario(rs.getString(3));
                    usuario.setCorreo(rs.getString(4));
                    usuario.setContrasena(rs.getString(5));
                    usuario.setTipo(TipoUsuario.valueOf(rs.getString(6)));
                    usuario.setActivo(rs.getBoolean(7));

                    return usuario;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario save(Usuario usuario) throws SQLException {
        String sql = """
                insert into SIME_USUARIO
                (nombre_usuario,
                correo,
                contrasena,                
                rol) values (?,?,?,?)
                """;

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1,usuario.getNombreUsuario());
            pstm.setString(2, usuario.getCorreo());
            pstm.setString(3,usuario.getContrasena());
            pstm.setString(4,usuario.getTipo().name());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        usuario.setIdUsuario(newId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) throws SQLException {
        String sql = "UPDATE SIME_USUARIO SET nombre_usuario = ?, correo = ?, contrasena = ?, rol = ? WHERE id_usuario = ?";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, usuario.getNombreUsuario());
            pstm.setString(2, usuario.getCorreo());
            pstm.setString(3, usuario.getContrasena());
            pstm.setString(4, usuario.getTipo().name());
            pstm.setInt(5, usuario.getIdUsuario());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public void remove(Usuario usuario) throws SQLException {
        String sql = "UPDATE SIME_USUARIO SET activo = 0 WHERE id_usuario = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,usuario.getIdUsuario());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el alumno con ID: " + usuario.getIdUsuario());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
