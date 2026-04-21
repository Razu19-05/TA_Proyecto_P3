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

        RelacionFamiliar relacionFamiliar = new RelacionFamiliar();
//
        Alumno alu = new Alumno();
//        alu.setIdAlumno(1);
        Apoderado apo = new Apoderado();
//        apo.setIdApoderado(2);
//
//        relacionFamiliar.setApoderado(apo);
//        relacionFamiliar.setAlumno(alu);
//
//        relacionFamiliar.setTipoRelacion("aaa");
//        relacionFamiliar.setContactoEmergencia("98989898");
//        relacionFamiliar.setObservaciones("bbbbbb");
//
//        relacionFamiliarDAO.save(relacionFamiliar);
        List<RelacionFamiliar> relaciones = relacionFamiliarDAO.listAll();
        for (RelacionFamiliar rf : relaciones){
            alu = rf.getAlumno(); apo =rf.getApoderado();
            String datos_alu = alu.getNombres() + alu.getApellidoPaterno();
            String datos_apo = apo.getNombres() + apo.getApellidoPaterno();
            String txt = "Relacion \n" + datos_apo +" - " + datos_alu;

            System.out.println(txt);
        }
    }
}
