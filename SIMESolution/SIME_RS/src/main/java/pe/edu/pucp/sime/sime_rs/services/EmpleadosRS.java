package pe.edu.pucp.sime.sime_rs.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.SIME.BL.PersonalBLImpl;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.BL.impl.IPersonalBL;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.util.List;

@Path("EmpleadosRS")
@Produces(MediaType.APPLICATION_JSON)
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
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
