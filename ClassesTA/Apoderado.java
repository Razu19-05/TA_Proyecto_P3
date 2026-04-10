import java.util.List;
import java.util.ArrayList;

class Apoderado {
    private int idApoderado;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String direccion;
    private String correo;

    private List<Alumno> alumnosApoderados;





    //SETTERS Y GETTERS
    
    public int getIdApoderado(){
        return this.idApoderado;
    }

    public void setIdApoderado(int idApoderado){
        this.idApoderado = idApoderado;
    }

    public String getNombres() {
    return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Alumno> getAlumnosApoderados() {
        return this.alumnosApoderados;
    }

    public void setAlumnosApoderados(List<Alumno> alumnosApoderados) {
        this.alumnosApoderados = alumnosApoderados;
    }







}
