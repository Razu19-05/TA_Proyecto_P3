package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.SIME.BL.PagoBLImpl;
import pe.edu.pucp.SIME.BL.impl.IPagoBL;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;
import pe.edu.pucp.SIME.model.DTO.CambioEstadoPagoDTO;

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

    @GET
    @Path("listar_pagos_pendientes_alumno/{idAlumno}")
    public Response listarPagosPendientesAlumno(@PathParam("idAlumno") int idAlumno) {
        try{
            List<Pago> pagos = pagoBL.listarPagosPendientesAlumno(idAlumno);
            return Response.ok(pagos).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }
        @PUT
    @Path("actualizar/{idPago}")
    public Response actualizarPago(@PathParam("idPago") int idPago, Pago pagoAct) {
        try {
            pagoAct = pagoBL.actualizarPago(idPago,pagoAct);
            return Response.ok(pagoAct).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("eliminar/{id}")
    public Response eliminarPago(@PathParam("id") int id) {
        try{
            pagoBL.eliminarPago(id);
            return Response.noContent().build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("pagar/{idPago}")
    public Response pagarPago(
            @PathParam("idPago") int idPago,
            CambioEstadoPagoDTO request) {
        try {
            String observacion = request == null ? "" : request.getObservacion();

            Pago pago = pagoBL.pagarPago(idPago, observacion);

            return Response.ok(pago).build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("anular/{idPago}")
    public Response anularPago(
            @PathParam("idPago") int idPago,
            CambioEstadoPagoDTO request) {
        try {
            String observacion = request == null ? "" : request.getObservacion();

            Pago pago = pagoBL.anularPago(idPago, observacion);

            return Response.ok(pago).build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
