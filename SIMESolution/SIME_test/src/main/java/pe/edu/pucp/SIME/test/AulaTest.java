package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.AulaDAO;
import pe.edu.pucp.SIME.aula.impl.AulaDAOImpl;
import pe.edu.pucp.SIME.model.Aula;
import pe.edu.pucp.SIME.model.TipoAula;

public class AulaTest {
    public static void main(String[] args) {
        AulaDAO aulaDAO = new AulaDAOImpl();
        //Prueba de load
//        Aula aulaElegida = new Aula();
//        aulaElegida = aulaDAO.load(1);
//        System.out.println(aulaElegida.getCodigo());
        //Prueba de save
        Aula aulaGuarda = new Aula();
//        aulaGuarda.setCodigo("F404");
//        aulaGuarda.setActivo(1);
//        aulaGuarda.setCapacidad(30);
//        aulaGuarda.setTipoAula(TipoAula.AULA);
//        aulaDAO.save(aulaGuarda);
        //Prueba de update
        aulaGuarda.setIdAula(6);
//        aulaGuarda.setCapacidad(40);
//        aulaDAO.update(aulaGuarda);
        //prueba de Remove
//        aulaDAO.remove(aulaGuarda);

    }

}
