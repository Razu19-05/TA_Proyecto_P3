package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.apoderado.DAO.ApoderadoDAO;
import pe.edu.pucp.SIME.apoderado.impl.ApoderadoDAOImpl;
import pe.edu.pucp.SIME.apoderado.model.Apoderado;

import java.util.List;

public class TestApoderado {
    public static void main(String[] args) {
        ApoderadoDAO apoderadoDAO = new ApoderadoDAOImpl();
        //Prueba de save
        Apoderado newApoderado = new Apoderado();
//        newApoderado.setNombres("Julio");
//        newApoderado.setApellidoPaterno("Huarote");
//        newApoderado.setApellidoMaterno("CHAMPAC");
//        newApoderado.setDni("09823746");
//        newApoderado.setTelefono("994102409");
//        newApoderado.setDireccion("Chorrillos");
//        newApoderado.setCorreo("julioh72@hotmail.com");
//        apoderadoDAO.save(newApoderado);

        //Prueba de load
//        Apoderado apoderadoElegido = new Apoderado();
//        apoderadoElegido = apoderadoDAO.load(1);
//        System.out.println(apoderadoElegido.getNombres());

        //Prubea de remove
        newApoderado.setIdApoderado(1);
        apoderadoDAO.remove(newApoderado);

        //Prueba de listAll
        List<Apoderado> apoderados = apoderadoDAO.listAll();


    }
}
