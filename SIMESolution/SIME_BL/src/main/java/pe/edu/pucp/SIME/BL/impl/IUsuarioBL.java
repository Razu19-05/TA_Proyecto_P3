package pe.edu.pucp.SIME.BL.impl;
import pe.edu.pucp.SIME.model.gestionDePersonal.Usuario;
public interface IUsuarioBL {
    Usuario autenticarUsuario(String nombre,String password) throws Exception;
    Usuario registrarNuevoUsuario(Usuario nuevoUsuario)throws Exception;
    //void cambiarContrasena(int idUsuario, String contrasenaActual, String nuevaContrasena);
    void eliminar(Usuario user)throws Exception;
    Usuario obtenerPorId(int idUsuario)throws Exception;
}
