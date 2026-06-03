package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.RelacionFamiliarDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.sql.SQLException;

public class RelacionFamiliarTest {
    public static void main(String [] args) throws SQLException{
        RelacionFamiliarDAO relacionDAO = new RelacionFamiliarDAOImpl();
        PersonaDAO personaDAO = new PersonaDAOImpl();
        Persona persona = new Persona();
        RelacionFamiliar rf = relacionDAO.load(1);
        int id_per = rf.getPersona().getIdPersona();
        persona = personaDAO.load(id_per);
        rf.setPersona(persona);

        System.out.println(rf.getIdRelacionFamiliar());
        System.out.println(rf.getPersona().getNombres());
        TransactionContext.close();

    }
}
