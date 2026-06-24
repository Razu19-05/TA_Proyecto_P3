package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.util.List;

public interface IRelacionFamiliarBL
{
    List<RelacionFamiliar> listarApoderados(Integer idAlumno) throws Exception;
}
