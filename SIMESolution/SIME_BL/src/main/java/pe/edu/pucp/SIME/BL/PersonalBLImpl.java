package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IPersonalBL;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

public class PersonalBLImpl implements IPersonalBL {
    private PersonaDAO personaDAO = new PersonaDAOImpl();

    @Override
    public ResumenPersonalDTO cargarTarjetasResumen() throws Exception{
        try {
            return personaDAO.obtenerEstadisticas();
        } catch (Exception e) {
            throw new Exception("Error al calcular las estadísticas: " + e.getMessage());
        }
    }
    @Override
    public void registrarNuevoEmpleado(Persona empleado) throws Exception {
        try{
            TransactionContext.getConnection();
            Persona existente = personaDAO.buscarPorDni(empleado.getDni());
            if (existente != null) {
                throw new Exception("No se puede registrar. El DNI " + empleado.getDni() + " ya pertenece a otro trabajador.");
            }

            empleado.setActivo(true);
            personaDAO.save(empleado);

            TransactionContext.commit();
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception(e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
}
