package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

public interface IAlumnoBL {
    Alumno buscarAlumnoPorDNI(String DNI)throws Exception;
    Alumno buscarAlumnoPorCriterio(String criterio) throws Exception;
    Alumno actualizar(Alumno alumno) throws Exception;
    Alumno insertar(Alumno alumno) throws Exception;
    boolean existeAlumnoPorDni(String dni) throws Exception;
}
