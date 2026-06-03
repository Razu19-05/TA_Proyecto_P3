package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IUsuarioBL;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.UsuarioDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.UsuarioDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionDePersonal.Usuario;

public class UsuarioBLImpl implements IUsuarioBL {
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    @Override
    public Usuario autenticarUsuario(String nombreUsuario, String password) throws Exception {
        Usuario usuario = usuarioDAO.buscarPorNombre(nombreUsuario);
        if (usuario == null) {
            // El usuario no existe
            throw new Exception("Las credenciales ingresadas no coinciden con los registros.");
        }

        if (!usuario.isActivo()) {
            // El usuario existe, pero fue despedido o bloqueado
            throw new Exception("Su cuenta se encuentra inactiva. Contacte al administrador.");
        }
        if (!usuario.getContrasena().equals(password)) {
            throw new Exception("Las credenciales ingresadas no coinciden con los registros.");
        }
        return usuario;

    }

    @Override
    public Usuario registrarNuevoUsuario(Usuario nuevoUsuario) throws Exception{
        try {
            TransactionContext.getConnection();

            // REGLA: No pueden existir dos personas con el mismo "nombre_usuario"
            Usuario usuarioExistente = usuarioDAO.buscarPorNombre(nuevoUsuario.getNombreUsuario());

            if (usuarioExistente != null) {
                throw new Exception("Error: El nombre de usuario '" + nuevoUsuario.getNombreUsuario() + "' ya está en uso.");
            }

            nuevoUsuario.setActivo(true);

            // Guardamos
            Usuario usuarioGuardado = usuarioDAO.save(nuevoUsuario);

            TransactionContext.commit();
            return usuarioGuardado;

        } catch (Exception e) {
            TransactionContext.rollback();
            throw e;
        } finally {
            TransactionContext.close();
        }

    }
    public void eliminar(Usuario user) throws Exception{
        try{
            TransactionContext.getConnection();

            usuarioDAO.remove(user);

            TransactionContext.commit();

        }catch (Exception e) {
            TransactionContext.rollback();
            throw e;
        } finally {
            TransactionContext.close();
        }

    }
    public Usuario obtenerPorId(int idUsuario)throws Exception{
        return usuarioDAO.load(idUsuario);
    }
}


