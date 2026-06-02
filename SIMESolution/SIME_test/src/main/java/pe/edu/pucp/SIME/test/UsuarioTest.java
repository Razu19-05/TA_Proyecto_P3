package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.UsuarioDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.UsuarioDAOImpl;
import pe.edu.pucp.SIME.model.gestionDePersonal.Usuario;

import java.sql.SQLException;

public class UsuarioTest {
    public static void main(String [] args) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        Usuario usuario = usuarioDAO.load(1);

        System.out.println(usuario.getIdUsuario());
        System.out.println(usuario.getNombreUsuario());
    }

}
