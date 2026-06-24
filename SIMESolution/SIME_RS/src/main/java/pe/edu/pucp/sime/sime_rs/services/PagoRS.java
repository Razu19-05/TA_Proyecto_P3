package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.PagoBLImpl;
import pe.edu.pucp.SIME.BL.impl.IPagoBL;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;

import java.util.List;

@Path("PagoRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagoRS {
    private IPagoBL pagoBL = new PagoBLImpl();

    @GET
    @Path("listar_pagos_alumno/{idAlumno}")
    public Response listarPagosAlumno(@PathParam("idAlumno") int idAlumno) {
        try{
            List<Pago> pagos = pagoBL.listarPagosAlumno(idAlumno);
            return Response.ok(pagos).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }
}
