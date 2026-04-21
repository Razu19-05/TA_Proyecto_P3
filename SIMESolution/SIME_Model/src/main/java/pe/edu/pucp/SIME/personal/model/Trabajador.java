package pe.edu.pucp.SIME.personal.model;

import pe.edu.pucp.SIME.estudiante.model.Alumno;
import java.util.List;

public class Trabajador {
    protected int idTrabajador;
    protected String nombres;
    protected String apellidoPaterno;
    protected String getApellidoMaterno;
    protected String dni;
    protected String telefono;
    protected String correo;
    protected String direccion;

    protected List<Alumno> alumnos;

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
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

    public String getGetApellidoMaterno() {
        return getApellidoMaterno;
    }

    public void setGetApellidoMaterno(String getApellidoMaterno) {
        this.getApellidoMaterno = getApellidoMaterno;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
