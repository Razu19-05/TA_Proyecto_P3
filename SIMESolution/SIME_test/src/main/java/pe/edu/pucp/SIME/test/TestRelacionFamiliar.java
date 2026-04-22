package pe.edu.pucp.SIME.test;


import pe.edu.pucp.SIME.apoderado.DAO.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.apoderado.impl.RelacionFamiliarDAOImpl;
import pe.edu.pucp.SIME.apoderado.model.Apoderado;
import pe.edu.pucp.SIME.apoderado.model.RelacionFamiliar;
import pe.edu.pucp.SIME.estudiante.model.Alumno;

import java.util.List;

public class TestRelacionFamiliar {
    public static void main(String[] args) {
        RelacionFamiliarDAO relacionFamiliarDAO = new RelacionFamiliarDAOImpl();

        //Prueba Save
        RelacionFamiliar relacionFamiliar = new RelacionFamiliar();
        Alumno alu = new Alumno();
//        alu.setIdAlumno(1);
        Apoderado apo = new Apoderado();
//        apo.setIdApoderado(1);
//        relacionFamiliar.setApoderado(apo);
//        relacionFamiliar.setAlumno(alu);
//
//        relacionFamiliar.setTipoRelacion("Padre");
//        relacionFamiliar.setContactoEmergencia("98998999");
//        relacionFamiliar.setObservaciones("Está autorizado para retirar al menor");
//        relacionFamiliarDAO.save(relacionFamiliar);

        //Prueba load
//            relacionFamiliar = relacionFamiliarDAO.load(1,1);
//            System.out.println(relacionFamiliar.getObservaciones());
        //Prueba Listall
//        List<RelacionFamiliar> relaciones = relacionFamiliarDAO.listAll();
//            for (RelacionFamiliar rf : relaciones){
//            alu = rf.getAlumno(); apo =rf.getApoderado();
//            String datos_alu = alu.getNombres() + " " + alu.getApellidoPaterno();
//            String datos_apo = apo.getNombres() + " " + apo.getApellidoPaterno();
//            String txt = "Relacion \n" + datos_apo +" - " + datos_alu + "\n";
//
//            System.out.println(txt);
//        }
        //Prueba remove hace delete a la relacion
//        alu.setIdAlumno(7); apo.setIdApoderado(1);
//        relacionFamiliar.setAlumno(alu); relacionFamiliar.setApoderado(apo);
//
//        relacionFamiliarDAO.remove(relacionFamiliar);

    }
}
