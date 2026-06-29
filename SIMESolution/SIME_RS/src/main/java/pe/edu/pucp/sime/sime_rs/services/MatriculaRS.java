package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.MatriculaBLImpl;
import pe.edu.pucp.SIME.BL.MatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.BL.impl.IMatriculaBL;
import pe.edu.pucp.SIME.BL.impl.IMatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoRequestDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoResponseDTO;
import pe.edu.pucp.SIME.model.DTO.VacanteMatriculaDTO;

import java.util.List;

@Path("MatriculaRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatriculaRS {

	private final IMatriculaAlumnoNuevoBL matriculaAlumnoNuevoBL =
			new MatriculaAlumnoNuevoBL();
	private final IMatriculaBL matriculaBL = new MatriculaBLImpl();

	@GET
	@Path("vacantes")
	public Response listarVacantes(
			@QueryParam("periodo") String periodo,
			@QueryParam("nivel") String nivel,
			@QueryParam("grado") String grado) {
		try {
			List<VacanteMatriculaDTO> vacantes =
					matriculaAlumnoNuevoBL.listarVacantes(periodo, nivel, grado);

			return Response.ok(vacantes).build();

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage())
					.build();
		}
	}

	@POST
	@Path("alumnoNuevo")
	public Response matricularAlumnoNuevo(MatriculaAlumnoNuevoRequestDTO request) {
		try {
			MatriculaAlumnoNuevoResponseDTO response =
					matriculaAlumnoNuevoBL.matricularAlumnoNuevo(request);

			return Response.status(Response.Status.CREATED)
					.entity(response)
					.build();

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage())
					.build();
		}
	}
	@POST
	@Path("alumnoExistente")
	public Response matricularAlumnoExistente(SolicitudMatriculaDTO request) {
		try {
			matriculaBL.procesarMatriculaCompleta(request);
			return Response.status(Response.Status.CREATED)
					.entity(request)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage())
					.build();
		}
	}
}
