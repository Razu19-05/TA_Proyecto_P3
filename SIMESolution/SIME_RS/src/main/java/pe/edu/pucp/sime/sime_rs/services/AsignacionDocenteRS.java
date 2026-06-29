package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.AsignacionDocenteBL;
import pe.edu.pucp.SIME.BL.impl.IAsignacionDocenteBL;
import pe.edu.pucp.SIME.model.DTO.AsignacionDocenteRequestDTO;
import pe.edu.pucp.SIME.model.DTO.ProfesorAulaDTO;

import java.util.List;

@Path("AsignacionDocenteRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AsignacionDocenteRS {

    private final IAsignacionDocenteBL asignacionDocenteBL = new AsignacionDocenteBL();

    @GET
    @Path("listarPorAula/{idAula}")
    public Response listarPorAula(@PathParam("idAula") int idAula) {
        try {
            List<ProfesorAulaDTO> lista =
                    asignacionDocenteBL.listarPorAula(idAula);

            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("listarPorMatriculaCabecera/{idMatriculaCabecera}")
    public Response listarPorMatriculaCabecera(
            @PathParam("idMatriculaCabecera") int idMatriculaCabecera) {
        try {
            List<ProfesorAulaDTO> lista =
                    asignacionDocenteBL.listarPorMatriculaCabecera(idMatriculaCabecera);

            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("profesoresDisponibles")
    public Response listarProfesoresDisponibles(@QueryParam("criterio") String criterio) {
        try {
            List<ProfesorAulaDTO> lista =
                    asignacionDocenteBL.listarProfesoresDisponibles(criterio);

            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("asignar")
    public Response asignar(AsignacionDocenteRequestDTO request) {
        try {
            int idGenerado = asignacionDocenteBL.asignarDocente(request);

            return Response.status(Response.Status.CREATED)
                    .entity(idGenerado)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("actualizar/{idAsignacionDocente}")
    public Response actualizar(
            @PathParam("idAsignacionDocente") int idAsignacionDocente,
            AsignacionDocenteRequestDTO request) {
        try {
            boolean ok =
                    asignacionDocenteBL.actualizarAsignacion(idAsignacionDocente, request);

            if (!ok) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se pudo actualizar la asignación docente.")
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
    @Path("eliminar/{idAsignacionDocente}")
    public Response eliminar(@PathParam("idAsignacionDocente") int idAsignacionDocente) {
        try {
            boolean ok =
                    asignacionDocenteBL.eliminarAsignacion(idAsignacionDocente);

            if (!ok) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se pudo eliminar la asignación docente.")
                        .build();
            }

            return Response.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}