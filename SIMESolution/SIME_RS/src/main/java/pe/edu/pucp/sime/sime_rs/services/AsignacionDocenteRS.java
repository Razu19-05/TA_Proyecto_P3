package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.AsignacionDocenteBL;
import pe.edu.pucp.SIME.BL.impl.IAsignacionDocenteBL;
import pe.edu.pucp.SIME.model.DTO.ProfesorDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.AsignacionDocente;

import java.util.List;

@Path("AsignacionDocenteRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AsignacionDocenteRS {
    private IAsignacionDocenteBL asignacionBL = new AsignacionDocenteBL();

    @GET
    @Path("listar")
    public Response listarDocentes(
            @QueryParam("nivel") String nivel,
            @QueryParam("grado") String grado,
            @QueryParam("anio") int anio) {
        try {
            List<ProfesorDTO> lista = asignacionBL.obtenerProfesoresPorAula(nivel, grado, anio);
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    @DELETE
    @Path("eliminar/{id}")
    public Response eliminarProfesor(@PathParam("id") int id) {
        try{
            AsignacionDocente asignacionDocente = new AsignacionDocente();
            asignacionDocente.setIdAsignacionDocente(id);
            asignacionBL.eliminarAsignacionDocente(asignacionDocente);
            return Response.noContent().build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
