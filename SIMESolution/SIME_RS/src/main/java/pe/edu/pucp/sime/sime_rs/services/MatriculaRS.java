package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.MatriculaBLImpl;
import pe.edu.pucp.SIME.BL.impl.IMatriculaBL;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;

@Path("MatriculaRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatriculaRS {
	private IMatriculaBL matriculaBL = new MatriculaBLImpl();

	@GET
	@Path("vacantes/{idMatriculaCabecera}")
	public Response verificarVacantes(@PathParam("idMatriculaCabecera") int idMatriculaCabecera) {
		try {
			int disponibles = matriculaBL.verificarVacantesDisponibles(idMatriculaCabecera);
			return Response.ok(disponibles).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("vacantes")
	public Response verificarVacantesPorNivelGrado(@QueryParam("nivel") String nivel, @QueryParam("grado") String grado) {
		try {
			if (nivel == null || grado == null) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Se requieren los parámetros 'nivel' y 'grado'").build();
			}
			int disponibles = matriculaBL.verificarVacantesPorNivelGrado(nivel, grado);
			return Response.ok(disponibles).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("detalle/{idAlumno}")
	public Response cargarMatriculaAlumno(@PathParam("idAlumno") int idAlumno) {
		try{
			MatriculaDetalle matriculaDetalle = matriculaBL.cargarMatriculaAlumno(idAlumno);
			return Response.ok(matriculaDetalle).build();
		} catch (Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}


