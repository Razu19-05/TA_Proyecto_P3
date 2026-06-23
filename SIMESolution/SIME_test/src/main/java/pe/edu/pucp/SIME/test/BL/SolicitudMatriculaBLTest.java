package pe.edu.pucp.SIME.test.BL;

import pe.edu.pucp.SIME.BL.MatriculaBLImpl;
import pe.edu.pucp.SIME.model.DTO.ApoderadoDetalleDTO;
import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.TipoRelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionDePersonal.TipoPersona;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SolicitudMatriculaBLTest {
    public static void main(String[] args){
        MatriculaBLImpl matriculaBL = new MatriculaBLImpl();

        // TO vacío
//        SolicitudMatriculaDTO solicitud = new SolicitudMatriculaDTO();
//
//        Alumno estudiantePrueba = new Alumno();
//        estudiantePrueba.setIdAlumno(0); // 0 significa que es nuevo y hará INSERT
//        estudiantePrueba.setDni("77889900");
//        estudiantePrueba.setNombres("Alumno Test");
//        estudiantePrueba.setApellidoPaterno("Prueba");
//        estudiantePrueba.setApellidoMaterno("Sistema");
//        try{
//            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//            Date fechaNacimiento = formato.parse("2015-02-17");
//            estudiantePrueba.setFechaNacimiento(fechaNacimiento);
//        } catch (Exception e){
//            System.err.println("Error: Formato de fecha incorrecto.");
//        }
//
//        estudiantePrueba.setAlumnoNuevo(true);
//        solicitud.setEstudiante(estudiantePrueba);
//
//        // Apoderado
//        Persona apoderadoPrueba = new Persona();
//        apoderadoPrueba.setIdPersona(0); // Nuevo
//        apoderadoPrueba.setDni("11223344");
//        apoderadoPrueba.setNombres("carlos");
//        apoderadoPrueba.setApellidoPaterno("Perez");
//        apoderadoPrueba.setApellidoMaterno("Tuesta");
//        apoderadoPrueba.setTelefono("999888777");
//        apoderadoPrueba.setTipo(TipoPersona.EXTERNO);
//
//        ApoderadoDetalleDTO detalleFamiliar = new ApoderadoDetalleDTO();
//        detalleFamiliar.setApoderado(apoderadoPrueba);
//        detalleFamiliar.setParentesco(TipoRelacionFamiliar.PADRE);
//        detalleFamiliar.setContactoEmergencia(true);
//        detalleFamiliar.setObservacionesFamiliares("Prueba desde Java Main");
//
//        // Metemos al apoderado en la lista del DTO principal
//        solicitud.getListaApoderados().add(detalleFamiliar);
//        solicitud.setIdMatriculaCabecera(1);
//        //sin descuentos
//        solicitud.setIdTipoDescuento(0);
//        solicitud.setPorcentajeDescuentoAplicar(0.0);
//
//        try {
//            matriculaBL.procesarMatriculaCompleta(solicitud);
//            System.out.println("La matrícula se guardó correctamente en las 6 tablas.");
//
//        } catch (Exception e) {
//            System.err.println("ERROR DURANTE LA MATRÍCULA: " + e.getMessage());
//            e.printStackTrace();
//        }

    }
}
