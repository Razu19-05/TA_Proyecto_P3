package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IPersonalBL;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.sql.SQLException;
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
    public Persona registrarNuevoEmpleado(Persona empleado) throws Exception {
        try{
            TransactionContext.getConnection();
            empleado = personaDAO.save(empleado);
            TransactionContext.commit();
            return empleado;
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
            TransactionContext.commit();
            return personaAct;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al listar empleados: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Persona buscarEmpleadoPorId(Integer id) throws Exception {
        try{
            TransactionContext.getConnection();
            Persona persona = personaDAO.load(id);
            TransactionContext.commit();
            return persona;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al buscar empleado: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public void eliminarEmpleado(Persona empleado) throws Exception {
        try{
            TransactionContext.getConnection();
            personaDAO.remove(empleado);
            TransactionContext.commit();
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al listar empleados: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Persona buscarProfesorPorDni(String dni) throws Exception {
        try{
            TransactionContext.getConnection();
            Persona profesor = personaDAO.buscarProfesorPorDni(dni);
            TransactionContext.commit();
            return profesor;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al listar empleados: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Persona buscarPersonaPorDni(String dni) throws Exception {
        try{
            TransactionContext.getConnection();
            Persona persona = personaDAO.buscarPorDni(dni);
            TransactionContext.commit();
            return persona;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al buscar persona por DNI: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
}
