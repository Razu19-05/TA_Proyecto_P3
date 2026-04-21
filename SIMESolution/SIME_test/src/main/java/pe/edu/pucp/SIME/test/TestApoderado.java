package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.apoderado.DAO.ApoderadoDAO;
import pe.edu.pucp.SIME.apoderado.impl.ApoderadoDAOImpl;
import pe.edu.pucp.SIME.apoderado.model.Apoderado;

public class TestApoderado {
    public static void main(String[] args) {
        ApoderadoDAO apoderadoDAO = new ApoderadoDAOImpl();
//        Apoderado apoderado = apoderadoDAO.load(1);
//        System.out.println(apoderado.getNombres());

        Apoderado newApoderado = new Apoderado();
        newApoderado.setNombres("Daniel");
        newApoderado.setApellidoPaterno("Ramirez");
        newApoderado.setApellidoMaterno("Tuesta");
        newApoderado.setDni("72512121");
        apoderadoDAO.save(newApoderado);
    }
}
