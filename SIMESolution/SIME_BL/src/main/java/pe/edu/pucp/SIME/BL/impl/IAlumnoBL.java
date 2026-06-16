package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

public interface IAlumnoBL {
    Alumno buscarAlumnoPorDNI(String DNI)throws Exception;
    Alumno actualizar(Alumno alumno) throws Exception;
}
