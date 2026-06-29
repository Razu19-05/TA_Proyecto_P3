package pe.edu.pucp.SIME.BL.servicios;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.RelacionFamiliarDAOImpl;
import pe.edu.pucp.SIME.model.DTO.ApoderadoNuevoDTO;

import java.util.List;

public class ApoderadoMatriculaAlumnoNuevoBL {

    private final RelacionFamiliarDAO relacionFamiliarDAO =
            new RelacionFamiliarDAOImpl();

    public void registrarApoderadosAlumnoNuevo(
            int idAlumno,
            List<ApoderadoNuevoDTO> apoderados
    ) throws Exception {

        if (idAlumno <= 0) {
            throw new Exception("El ID del alumno no es válido para registrar apoderados.");
        }

        if (apoderados == null || apoderados.isEmpty()) {
            throw new Exception("Debe registrar al menos un apoderado.");
        }

        for (ApoderadoNuevoDTO apoderado : apoderados) {
            int idRelacion = relacionFamiliarDAO.insertarApoderadoAlumno(
                    idAlumno,
                    apoderado
            );

            if (idRelacion <= 0) {
                throw new Exception(
                        "No se pudo registrar o vincular el apoderado con DNI: "
                                + apoderado.getDni()
                );
            }
        }
    }
}