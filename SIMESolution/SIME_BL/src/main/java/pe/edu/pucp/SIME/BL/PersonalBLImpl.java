package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IPersonalBL;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.util.List;

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

    @Override
    public List<Persona> listarEmpleados()throws Exception{
        try{
            TransactionContext.getConnection();
            List<Persona> personas = personaDAO.listarEmpleados();
            TransactionContext.commit();
            return personas;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al listar empleados: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Persona actualizarEmpleado(Persona empleado) throws Exception {
        try{
            TransactionContext.getConnection();
            Persona personaAct = personaDAO.update(empleado);
            return personaAct;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al listar empleados: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public void eliminarEmpleado(Persona empleado) throws Exception {
        try{
            TransactionContext.getConnection();
            personaDAO.remove(empleado);
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al listar empleados: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

}
