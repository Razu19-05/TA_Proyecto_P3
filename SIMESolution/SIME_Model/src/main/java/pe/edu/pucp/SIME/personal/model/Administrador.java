package pe.edu.pucp.SIME.personal.model;

public class Administrador extends Trabajador{
    private String cargo;

    public String getCargo(){
        return this.cargo;
    }
    public void setCargo(String cargo){
        this.cargo = cargo;
    }
}
