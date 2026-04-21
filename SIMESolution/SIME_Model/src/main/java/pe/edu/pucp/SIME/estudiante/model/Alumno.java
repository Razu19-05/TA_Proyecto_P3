package pe.edu.pucp.SIME.estudiante.model;

import pe.edu.pucp.SIME.apoderado.model.Apoderado;
import pe.edu.pucp.SIME.aula.model.GradoSeccion;
import pe.edu.pucp.SIME.matricula.model.Matricula;
import pe.edu.pucp.SIME.personal.model.Trabajador;

import java.util.Date;
import java.util.List;

public class Alumno {
    private int idAlumno;
    private String DNI;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccion;
    private String telefono;
    private String correo;
    private boolean estado; //activo, retirado
    private boolean alumnoNuevo;
    private double montoInscripcion;
    private double montoExamenPsicologico;
    private Date fechaDeNacimiento;
    private int edad;

    private List<Matricula> matriculas; // relación 1:1 (actual)
    private List<Apoderado> apoderados;
    private List<Trabajador> trabajadores;
    private Descuento descuento;
    private List<DeudaPasada> deudasPasadas;
    private GradoSeccion gradoSeccion;


    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public boolean isEstadoAlumno() {
        return estado;
    }

    public void setEstadoAlumno(boolean estadoAlumno) {
        this.estado = estadoAlumno;
    }

    public boolean isAlumnoNuevo() {
        return alumnoNuevo;
    }

    public void setAlumnoNuevo(boolean alumnoNuevo) {
        this.alumnoNuevo = alumnoNuevo;
    }

    public double getMontoInscripcion() {
        return montoInscripcion;
    }

    public void setMontoInscripcion(double montoInscripcion) {
        this.montoInscripcion = montoInscripcion;
    }

    public double getMontoExamenPsicologico() {
        return montoExamenPsicologico;
    }

    public void setMontoExamenPsicologico(double montoExamenPsicologico) {
        this.montoExamenPsicologico = montoExamenPsicologico;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public List<Apoderado> getApoderados() {
        return apoderados;
    }

    public void setApoderados(List<Apoderado> apoderados) {
        this.apoderados = apoderados;
    }

    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public List<DeudaPasada> getDeudasPasadas() {
        return deudasPasadas;
    }

    public void setDeudasPasadas(List<DeudaPasada> deudasPasadas) {
        this.deudasPasadas = deudasPasadas;
    }

    public GradoSeccion getGradoSeccion() {
        return gradoSeccion;
    }

    public void setGradoSeccion(GradoSeccion gradoSeccion) {
        this.gradoSeccion = gradoSeccion;
    }
}
