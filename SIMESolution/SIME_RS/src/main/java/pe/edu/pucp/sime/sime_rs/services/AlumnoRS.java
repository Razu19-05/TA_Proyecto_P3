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
