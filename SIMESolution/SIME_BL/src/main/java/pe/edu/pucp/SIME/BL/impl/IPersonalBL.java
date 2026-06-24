package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.sql.SQLException;
import java.util.List;

public interface IPersonalBL {
    Persona registrarNuevoEmpleado(Persona empleado) throws Exception;
    ResumenPersonalDTO cargarTarjetasResumen() throws Exception;
    List<Persona> listarEmpleados()throws Exception;
    void eliminarEmpleado(Persona empleado) throws Exception;
    Persona actualizarEmpleado(Persona empleado) throws Exception;
    Persona buscarEmpleadoPorId(Integer id) throws Exception;
    Persona buscarProfesorPorDni(String dni) throws Exception;
}
