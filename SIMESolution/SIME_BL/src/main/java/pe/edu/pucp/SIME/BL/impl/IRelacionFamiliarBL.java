package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.ApoderadoNuevoDTO;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.util.List;

public interface IRelacionFamiliarBL
{
    List<RelacionFamiliar> listarApoderados(Integer idAlumno) throws Exception;

    boolean existePersonaPorDni(String dni) throws Exception;

    int agregarApoderado(String dniAlumno, ApoderadoNuevoDTO apoderado) throws Exception;

    void editarApoderado(int idRelacionFamiliar, ApoderadoNuevoDTO apoderado) throws Exception;

    void eliminarApoderado(int idRelacionFamiliar) throws Exception;
}
