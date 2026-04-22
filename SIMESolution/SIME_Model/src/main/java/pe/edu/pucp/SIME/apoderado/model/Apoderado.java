package pe.edu.pucp.SIME.apoderado.model;

import pe.edu.pucp.SIME.estudiante.model.Alumno;
import java.util.List;

public class Apoderado {
    private int idApoderado;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String telefono;
    private String direccion;
    private String correo;
    private int activo; //para eliminación lógica de la db

    private List<Alumno> alumnosApoderados;

    public int getIdApoderado() {
        return idApoderado;
    }

    public void setIdApoderado(int idApoderado) {
        this.idApoderado = idApoderado;
    }


    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public List<Alumno> getAlumnosApoderados() {
        return alumnosApoderados;
    }

    public void setAlumnosApoderados(List<Alumno> alumnosApoderados) {
        this.alumnosApoderados = alumnosApoderados;
    }
}
