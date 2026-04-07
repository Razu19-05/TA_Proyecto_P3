class Alumno {
    private int idAlumno;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private boolean estadoAlumno; // activo, retirado
    private int edad;
    private boolean discapacidad;
    private String correo;
    
    Matricula matricula; // relación 1:1 (actual)
}
