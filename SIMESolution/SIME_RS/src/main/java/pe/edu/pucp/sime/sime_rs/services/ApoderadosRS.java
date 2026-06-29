package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.AlumnoBLImpl;
import pe.edu.pucp.SIME.BL.RelacionFamiliarBLImpl;
import pe.edu.pucp.SIME.BL.impl.IAlumnoBL;
import pe.edu.pucp.SIME.BL.impl.IRelacionFamiliarBL;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;

import java.util.List;

@Path("ApoderadosRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApoderadosRS {
    private IAlumnoBL alumnoBL = new AlumnoBLImpl();
    private IRelacionFamiliarBL relacionFamiliarBL = new RelacionFamiliarBLImpl();

    @GET
    @Path("listar/{dni}")
    public Response buscarApoderados(@PathParam("dni") String dni){
        try{
            Alumno alumno = alumnoBL.buscarAlumnoPorDNI(dni);
            List<RelacionFamiliar> apoderados = relacionFamiliarBL.listarApoderados(alumno.getIdAlumno());
            return Response.ok(apoderados).build();
        }catch(Exception e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @GET
    @Path("existe-dni/{dni}")
    public Response existeApoderadoPorDni(@PathParam("dni") String dni) {
        try {
            if (dni == null || !dni.matches("\\d{8}")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El DNI debe tener exactamente 8 dígitos numéricos.")
                        .build();
            }

            boolean existe = relacionFamiliarBL.existePersonaPorDni(dni);

            return Response.ok(existe).build();
        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


}
