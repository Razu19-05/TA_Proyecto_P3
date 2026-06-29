package pe.edu.pucp.sime.sime_rs.services;



import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.AulaBL;
import pe.edu.pucp.SIME.BL.impl.IAulaBL;
import pe.edu.pucp.SIME.model.DTO.AulaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;

import java.util.List;

@Path("AulaRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AulaRS {

    private IAulaBL aulaBL = new AulaBL();

    @GET
    @Path("listar")
    public Response listar(
            @QueryParam("periodo") String periodo,
            @QueryParam("nivel") String nivel,
            @QueryParam("grado") String grado,
            @QueryParam("estado") String estado,
            @QueryParam("codigo") String codigo) {
        try {
            List<AulaDTO> lista = aulaBL.listarAulas(periodo, nivel, grado, estado, codigo);
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("detalle/{id}")
    public Response detalle(@PathParam("id") int id) {
        try {
            AulaDTO aula = aulaBL.obtenerDetalleAula(id);

            if (aula == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontró el aula con ID: " + id)
                        .build();
            }

            return Response.ok(aula).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("crear")
    public Response crear(Aula aula) {
        try {
            Aula registrada = aulaBL.guardarAula(aula);
            return Response.status(Response.Status.CREATED).entity(registrada).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("actualizar/{id}")
    public Response actualizar(@PathParam("id") int id, Aula aula) {
        try {
            aula.setIdAula(id);
            Aula actualizada = aulaBL.actualizarAula(aula);
            return Response.ok(actualizada).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("capacidad/{id}")
    public Response actualizarCapacidad(@PathParam("id") int id, @QueryParam("capacidad") int capacidad) {
        try {
            boolean ok = aulaBL.actualizarCapacidad(id, capacidad);

            if (!ok) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se pudo actualizar la capacidad.")
                        .build();
            }

            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("eliminar/{id}")
    public Response eliminar(@PathParam("id") int id) {
        try {
            aulaBL.eliminarAula(id);
            return Response.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}