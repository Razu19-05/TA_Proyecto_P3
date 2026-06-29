package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.AlumnoBLImpl;
import pe.edu.pucp.SIME.BL.impl.IAlumnoBL;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

@Path("AlumnoRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlumnoRS {
    private IAlumnoBL alumnoBL = new AlumnoBLImpl();

    @GET
    @Path("buscar/{dni}")
    public Response buscarAlumnoPorDNI(@PathParam("dni") String dni) {
        try {
            return Response.ok(alumnoBL.buscarAlumnoPorDNI(dni)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("existe-dni/{dni}")
    public Response existeAlumnoPorDni(@PathParam("dni") String dni) {
        try {
            if (dni == null || !dni.matches("\\d{8}")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El DNI debe tener exactamente 8 dígitos numéricos.")
                        .build();
            }

            boolean existe = alumnoBL.existeAlumnoPorDni(dni);

            return Response.ok(existe).build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("buscar")
    public Response buscarAlumnoPorCriterio(@QueryParam("criterio") String criterio) {
        try {
            if (criterio == null || criterio.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Debe ingresar un criterio de búsqueda.")
                        .build();
            }

            Alumno alumno = alumnoBL.buscarAlumnoPorCriterio(criterio);

            if (alumno == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontró alumno con el criterio: " + criterio)
                        .build();
            }

            return Response.ok(alumno).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("actualizar/{dni}")
    public Response actualizarAlumnoPorDNI(@PathParam("dni") String dni, Alumno alumnoAct) {
        try{
            alumnoAct.setDni(dni);
            Alumno a = alumnoBL.buscarAlumnoPorDNI(dni);
            alumnoAct.setIdAlumno(a.getIdAlumno());
            alumnoAct = alumnoBL.actualizar(alumnoAct);
            return Response.ok(alumnoAct).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
