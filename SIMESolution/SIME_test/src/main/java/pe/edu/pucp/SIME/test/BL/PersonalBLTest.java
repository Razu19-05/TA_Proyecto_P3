package pe.edu.pucp.SIME.test.BL;

import pe.edu.pucp.SIME.BL.PersonalBLImpl;
import pe.edu.pucp.SIME.BL.impl.IPersonalBL;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionDePersonal.TipoPersona;

import java.util.ArrayList;
import java.util.List;

public class PersonalBLTest {
    public static void main(String[] args){
        IPersonalBL personalBL = new PersonalBLImpl();

//        try {
//            Persona nuevoProfesor = new Persona();
//
//            nuevoProfesor.setDni("99887766");
//            nuevoProfesor.setNombres("Roberto");
//            nuevoProfesor.setApellidoPaterno("Gómez");
//            nuevoProfesor.setApellidoMaterno("Bolaños");
//            nuevoProfesor.setTipo(TipoPersona.PROFESOR);
//            nuevoProfesor.setCargo("Docente Principal");
//            nuevoProfesor.setArea("Comunicaciones");
//            nuevoProfesor.setTelefono("911222333");
//            personalBL.registrarNuevoEmpleado(nuevoProfesor);
//            System.out.println("Profesor registrado correctamente en la base de datos.");
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        try {
            ResumenPersonalDTO estadisticas = personalBL.cargarTarjetasResumen();
            System.out.println("Datos obtenidos.");
            System.out.println(" Profesores activos: " + estadisticas.getCantidadProfesores());
            System.out.println(" Administrativos activos: " + estadisticas.getCantidadAdministrativos());
            System.out.println(" Personal de servicio: " + estadisticas.getCantidadServicio());
        } catch (Exception e) {
            System.err.println("ERROR AL CARGAR ESTADÍSTICAS: " + e.getMessage());
        }

        List<Persona> personas = new ArrayList<>();
        try{
            personas = personalBL.listarEmpleados();

            for(Persona p : personas){
                System.out.println(p.getIdPersona()+ "- " +p.getDni() + " " + p.getTipo() + " " + p.getTelefono());
            }
        }catch (Exception e) {
            System.err.println("ERROR AL CARGAR PEROSNAL: " + e.getMessage());
        }

        try{
            Persona persona = personas.get(4);
            persona.setTelefono("956999123");
            Persona personaAct = personalBL.actualizarEmpleado(persona);

            System.out.println("Persona actualizada " + personaAct.getTelefono());
        }catch(Exception e){
            System.err.println("ERROR AL ACTUALIZAR DATOS DEL PERSONAL: " + e.getMessage());
        }


    }
}
