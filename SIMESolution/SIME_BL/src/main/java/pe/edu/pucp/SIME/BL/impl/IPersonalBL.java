package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

public interface IPersonalBL {
    void registrarNuevoEmpleado(Persona empleado) throws Exception;
    ResumenPersonalDTO cargarTarjetasResumen() throws Exception;
}
