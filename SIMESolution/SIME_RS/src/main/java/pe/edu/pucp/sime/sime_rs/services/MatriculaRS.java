package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.MatriculaBLImpl;
import pe.edu.pucp.SIME.BL.MatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.BL.impl.IMatriculaBL;
import pe.edu.pucp.SIME.BL.impl.IMatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoRequestDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoResponseDTO;
import pe.edu.pucp.SIME.model.DTO.VacanteMatriculaDTO;
import pe.edu.pucp.SIME.model.DTO.HistorialMatriculaDTO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

	@GET
	@Path("listar_por_alumno/{idAlumno}")
	public Response listarMatriculasPorAlumno(@PathParam("idAlumno") int idAlumno) {
		try {
			List<MatriculaAlumnoDTO> matriculas =
					matriculaBL.listarMatriculasPorAlumno(idAlumno);

			return Response.ok(matriculas).build();

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		}
	}

	@GET
	@Path("historial")
	public Response listarHistorialMatriculas() {
		try {
			List<HistorialMatriculaDTO> historial =
					matriculaBL.listarHistorialMatriculas();

			return Response.ok(historial).build();

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		}
	}

	@GET
	@Path("historial/reporte")
	@Produces("application/pdf")
	public Response generarReporteHistorialMatriculas(
			@QueryParam("periodo") String periodo,
			@QueryParam("nivel") String nivel,
			@QueryParam("grado") String grado) {
		try {
			List<HistorialMatriculaDTO> historial =
					matriculaBL.listarHistorialMatriculas();

			List<HistorialMatriculaDTO> historialFiltrado = historial.stream()
					.filter(HistorialMatriculaDTO::isActivo)
					.filter(h -> coincideFiltro(h.getPeriodo(), periodo))
					.filter(h -> coincideFiltro(h.getNivel(), nivel))
					.filter(h -> coincideFiltro(h.getGrado(), grado))
					.collect(Collectors.toList());

			InputStream reporteStream = Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("reports/reporte_historial_matriculas.jrxml");

			if (reporteStream == null) {
				throw new Exception("No se encontró el archivo reporte_historial_matriculas.jrxml.");
			}

			JasperReport jasperReport =
					JasperCompileManager.compileReport(reporteStream);

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("titulo", "Reporte de Alumnos Matriculados");
			parametros.put("periodoFiltro", textoFiltro(periodo));
			parametros.put("nivelFiltro", textoFiltro(nivel));
			parametros.put("gradoFiltro", textoFiltro(grado));
			parametros.put("fechaGeneracion", new Date());
			parametros.put("totalRegistros", historialFiltrado.size());

			JRBeanCollectionDataSource dataSource =
					new JRBeanCollectionDataSource(historialFiltrado);

			JasperPrint jasperPrint =
					JasperFillManager.fillReport(jasperReport, parametros, dataSource);

			byte[] pdf =
					JasperExportManager.exportReportToPdf(jasperPrint);

			return Response.ok(pdf)
					.type("application/pdf")
					.header("Content-Disposition", "inline; filename=reporte_historial_matriculas.pdf")
					.build();

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.type("text/plain")
					.entity("Error al generar el reporte de historial: " + e.getMessage())
					.build();
		}

	}

	private boolean coincideFiltro(String valor, String filtro) {
		if (filtro == null || filtro.isBlank()) {
			return true;
		}

		if (valor == null) {
			return false;
		}

		return valor.trim().equalsIgnoreCase(filtro.trim());
	}

	private String textoFiltro(String filtro) {
		if (filtro == null || filtro.isBlank()) {
			return "Todos";
		}

		return filtro.trim();
	}
}
