package pe.edu.pucp.SIME.test.BL;

import pe.edu.pucp.SIME.BL.UsuarioBLImpl;
import pe.edu.pucp.SIME.model.gestionDePersonal.Usuario;

import static pe.edu.pucp.SIME.model.gestionDePersonal.TipoUsuario.PERSONAL_MATRICULA;

public class UsuarioBLTest {
    public static void main(String[] args){
        UsuarioBLImpl usuarioBL = new UsuarioBLImpl();

        System.out.println("\n[PRUEBA 1] Intento con credenciales correctas:");
        try {
            // matricula01" y "123456" con datos BD
            Usuario usuarioLogueado = usuarioBL.autenticarUsuario("matricula01", "123456");
            System.out.println(" Bienvenido, " + usuarioLogueado.getNombreUsuario());
        } catch (Exception e) {
            System.err.println("FALLO INESPERADO: " + e.getMessage());
        }

//        Usuario newUser = new Usuario();
//        newUser.setNombreUsuario("admin1");
//        newUser.setCorreo("admin@sime.edu");
//        newUser.setContrasena("hola1212");
//        newUser.setTipo(PERSONAL_MATRICULA);
//
//        try{
//            Usuario usuarioGuardado =  usuarioBL.registrarNuevoUsuario(newUser);
//        } catch (Exception e){
//            System.err.println("Error: " + e.getMessage());
//        }


//        try{
//            Usuario usuario = usuarioBL.obtenerPorId(1);
//            usuarioBL.eliminar(usuario);
//        } catch (Exception e){
//            System.err.println("Error: "+e.getMessage());
//        }



    }
}
