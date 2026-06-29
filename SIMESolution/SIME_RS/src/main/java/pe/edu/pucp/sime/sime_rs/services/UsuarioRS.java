package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.UsuarioBLImpl;
import pe.edu.pucp.SIME.BL.impl.IUsuarioBL;
import pe.edu.pucp.SIME.model.DTO.LoginRequestDTO;
import pe.edu.pucp.SIME.model.DTO.LoginResponseDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Usuario;

@Path("UsuarioRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioRS {

    private IUsuarioBL usuarioBL = new UsuarioBLImpl();

    @POST
    @Path("autenticar")
    public Response autenticar(LoginRequestDTO request) {
        try {
            if (request == null ||
                    request.getNombreUsuario() == null ||
                    request.getNombreUsuario().trim().isEmpty() ||
                    request.getContrasena() == null ||
                    request.getContrasena().trim().isEmpty()) {

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Debe ingresar usuario y contraseña.")
                        .build();
            }

            Usuario usuario = usuarioBL.autenticarUsuario(
                    request.getNombreUsuario().trim(),
                    request.getContrasena()
            );

            LoginResponseDTO response = new LoginResponseDTO();
            response.setIdUsuario(usuario.getIdUsuario());
            response.setNombreUsuario(usuario.getNombreUsuario());
            response.setCorreo(usuario.getCorreo());
            response.setRol(usuario.getTipo().name());

            return Response.ok(response).build();

        } catch (Exception e) {
            e.printStackTrace();

            String mensaje = e.getMessage();

            if (mensaje != null && mensaje.toLowerCase().contains("inactiva")) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(mensaje)
                        .build();
            }

            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuario o contraseña incorrectos.")
                    .build();
        }
    }
}