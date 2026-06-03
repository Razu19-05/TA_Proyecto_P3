package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.sql.SQLException;

public class PersonaTest {
    public static void main(String [] args) throws SQLException {
        PersonaDAO personaDAO = new PersonaDAOImpl();
        Persona persona = personaDAO.load(1);

        System.out.println(persona.getIdPersona());
        System.out.println(persona.getNombres());
        System.out.println(persona.getTipo());
        TransactionContext.close();
    }
}
