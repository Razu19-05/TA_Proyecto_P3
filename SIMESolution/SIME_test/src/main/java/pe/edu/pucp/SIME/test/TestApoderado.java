package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.apoderado.DAO.ApoderadoDAO;
import pe.edu.pucp.SIME.apoderado.impl.ApoderadoDAOImpl;
import pe.edu.pucp.SIME.apoderado.model.Apoderado;

public class TestApoderado {
    public static void main(String[] args) {
        ApoderadoDAO apoderadoDAO = new ApoderadoDAOImpl();
        Apoderado apoderado = apoderadoDAO.load(3);
        System.out.println(apoderado.getNombres());

        Apoderado newApoderado = new Apoderado();
        newApoderado.setNombres("Daniel");
        newApoderado.setApellidoPaterno("Ramire<");
        newApoderado.setApellidoMaterno("Tuesta");
        newApoderado.setDireccion("San Miguel");
        newApoderado.setTelefono("956888650");
        newApoderado.setCorreo("a20221462@pucp.edu.pe");
        apoderadoDAO.save(newApoderado);
    }
}
