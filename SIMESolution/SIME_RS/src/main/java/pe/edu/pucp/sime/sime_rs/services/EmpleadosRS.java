package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.PersonalBLImpl;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.BL.impl.IPersonalBL;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.util.List;

@Path("EmpleadosRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpleadosRS {

    private IPersonalBL personalBL = new PersonalBLImpl();

    @GET
    @Path("resumen")
    public ResumenPersonalDTO numEmpleados() {
        try {
            return personalBL.cargarTarjetasResumen();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("listar")
    public List<Persona> listEmpleados() {
        try {
            return personalBL.listarEmpleados();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("crear")
    public Response crearEmpleado(Persona persona) {
        try {
            persona = personalBL.registrarNuevoEmpleado(persona);
            return Response.status(Response.Status.CREATED).entity(persona).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("actualizar/{id}")
    public Response actulizarEmpleado(@PathParam("id") int id,Persona personaAct) {
        try {
            personaAct.setIdPersona(id);
            personaAct = personalBL.actualizarEmpleado(personaAct);
            return Response.ok(personaAct).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    @Path("eliminar/{id}")
    public Response eliminarEmpleado(@PathParam("id") int id) {
        try{
            Persona persona = personalBL.buscarEmpleadoPorId(id);
            personalBL.eliminarEmpleado(persona);
            return Response.noContent().build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}


