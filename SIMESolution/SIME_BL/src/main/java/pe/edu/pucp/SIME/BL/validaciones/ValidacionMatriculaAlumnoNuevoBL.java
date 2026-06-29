package pe.edu.pucp.SIME.BL.validaciones;

import pe.edu.pucp.SIME.model.DTO.ApoderadoNuevoDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoRequestDTO;

import java.util.HashSet;
import java.util.Set;

public class ValidacionMatriculaAlumnoNuevoBL {

    public void validarRequest(MatriculaAlumnoNuevoRequestDTO request) throws Exception {
        if (request == null) {
            throw new Exception("La solicitud de matrícula está vacía.");
        }

        validarAlumno(request);
        validarVacanteSeleccionada(request);
        validarApoderados(request);
    }

    public void validarFiltroVacantes(String periodo, String nivel, String grado) throws Exception {
        if (periodo == null || periodo.isBlank()) {
            throw new Exception("Debe seleccionar un periodo académico.");
        }

        if (nivel == null || nivel.isBlank()) {
            throw new Exception("Debe seleccionar un nivel.");
        }

        if (grado == null || grado.isBlank()) {
            throw new Exception("Debe seleccionar un grado.");
        }
    }

    private void validarAlumno(MatriculaAlumnoNuevoRequestDTO request) throws Exception {
        if (request.getAlumno() == null) {
            throw new Exception("Debe enviar los datos del alumno.");
        }

        String dni = request.getAlumno().getDni();

        if (dni == null || dni.isBlank()) {
            throw new Exception("El DNI del alumno es obligatorio.");
        }

        dni = dni.trim();

        if (!esDniValido(dni)) {
            throw new Exception("El DNI del alumno debe tener exactamente 8 dígitos numéricos.");
        }

        if (request.getAlumno().getNombres() == null ||
                request.getAlumno().getNombres().isBlank()) {
            throw new Exception("Los nombres del alumno son obligatorios.");
        }

        if (request.getAlumno().getApellidoPaterno() == null ||
                request.getAlumno().getApellidoPaterno().isBlank()) {
            throw new Exception("El apellido paterno del alumno es obligatorio.");
        }

        if (request.getAlumno().getApellidoMaterno() == null ||
                request.getAlumno().getApellidoMaterno().isBlank()) {
            throw new Exception("El apellido materno del alumno es obligatorio.");
        }

        if (request.getAlumno().getFechaNacimiento() == null ||
                request.getAlumno().getFechaNacimiento().isBlank()) {
            throw new Exception("La fecha de nacimiento del alumno es obligatoria.");
        }

        String telefono = request.getAlumno().getTelefono();

        if (telefono == null || telefono.isBlank()) {
            throw new Exception("El teléfono del alumno es obligatorio.");
        }

        telefono = telefono.trim();

        if (!esTelefonoCelularValido(telefono)) {
            throw new Exception("El teléfono del alumno debe tener 9 dígitos, empezar con 9 y contener solo números.");
        }

        if (request.getAlumno().getCorreo() == null ||
                request.getAlumno().getCorreo().isBlank()) {
            throw new Exception("El correo del alumno es obligatorio.");
        }

        if (request.getAlumno().getDireccion() == null ||
                request.getAlumno().getDireccion().isBlank()) {
            throw new Exception("La dirección del alumno es obligatoria.");
        }
    }

    private void validarVacanteSeleccionada(MatriculaAlumnoNuevoRequestDTO request) throws Exception {
        if (request.getIdMatriculaCabecera() <= 0) {
            throw new Exception("Debe seleccionar una vacante válida.");
        }
    }

    private void validarApoderados(MatriculaAlumnoNuevoRequestDTO request) throws Exception {
        if (request.getApoderados() == null || request.getApoderados().isEmpty()) {
            throw new Exception("Debe registrar al menos un apoderado.");
        }

        if (request.getApoderados().size() > 3) {
            throw new Exception("Solo se permite registrar hasta 3 apoderados.");
        }

        boolean tieneContactoEmergencia = false;
        Set<String> dnisApoderados = new HashSet<>();

        for (ApoderadoNuevoDTO apoderado : request.getApoderados()) {
            validarApoderado(apoderado);

            String dni = apoderado.getDni().trim();

            if (dnisApoderados.contains(dni)) {
                throw new Exception("No puede registrar dos apoderados con el mismo DNI en la misma matrícula.");
            }

            dnisApoderados.add(dni);

            if (apoderado.isContactoEmergencia()) {
                tieneContactoEmergencia = true;
            }
        }

        if (!tieneContactoEmergencia) {
            throw new Exception("Debe marcar al menos un apoderado como contacto de emergencia.");
        }
    }

    private void validarApoderado(ApoderadoNuevoDTO apoderado) throws Exception {
        if (apoderado == null) {
            throw new Exception("Los datos del apoderado no pueden estar vacíos.");
        }

        if (apoderado.getDni() == null || apoderado.getDni().isBlank()) {
            throw new Exception("El DNI del apoderado es obligatorio.");
        }

        String dni = apoderado.getDni().trim();

        if (!esDniValido(dni)) {
            throw new Exception("El DNI del apoderado debe tener exactamente 8 dígitos numéricos.");
        }

        if (apoderado.getNombres() == null || apoderado.getNombres().isBlank()) {
            throw new Exception("Los nombres del apoderado son obligatorios.");
        }

        if (apoderado.getApellidoPaterno() == null || apoderado.getApellidoPaterno().isBlank()) {
            throw new Exception("El apellido paterno del apoderado es obligatorio.");
        }

        if (apoderado.getApellidoMaterno() == null || apoderado.getApellidoMaterno().isBlank()) {
            throw new Exception("El apellido materno del apoderado es obligatorio.");
        }

        if (apoderado.getParentesco() == null || apoderado.getParentesco().isBlank()) {
            throw new Exception("El parentesco del apoderado es obligatorio.");
        }

        if (apoderado.getTelefono() == null || apoderado.getTelefono().isBlank()) {
            throw new Exception("El teléfono del apoderado es obligatorio.");
        }

        String telefono = apoderado.getTelefono().trim();

        if (!esTelefonoCelularValido(telefono)) {
            throw new Exception("El teléfono del apoderado debe tener 9 dígitos, empezar con 9 y contener solo números.");
        }

        if (apoderado.getCorreo() == null || apoderado.getCorreo().isBlank()) {
            throw new Exception("El correo del apoderado es obligatorio.");
        }

        if (apoderado.getDireccion() == null || apoderado.getDireccion().isBlank()) {
            throw new Exception("La dirección del apoderado es obligatoria.");
        }

        validarCamposSegunTipoApoderado(apoderado);
    }

    private void validarCamposSegunTipoApoderado(ApoderadoNuevoDTO apoderado) throws Exception {
        String tipo = apoderado.getTipo();

        if (tipo == null || tipo.isBlank()) {
            return;
        }

        tipo = tipo.trim().toUpperCase();

        if ("PROFESOR".equals(tipo) &&
                (apoderado.getEspecialidad() == null || apoderado.getEspecialidad().isBlank())) {
            throw new Exception("Si el apoderado es profesor, debe ingresar la especialidad.");
        }

        if ("ADMINISTRADOR".equals(tipo) &&
                (apoderado.getCargo() == null || apoderado.getCargo().isBlank())) {
            throw new Exception("Si el apoderado es administrador, debe ingresar el cargo.");
        }

        if ("PERSONAL_SERVICIO".equals(tipo) &&
                (apoderado.getArea() == null || apoderado.getArea().isBlank())) {
            throw new Exception("Si el apoderado es personal de servicio, debe ingresar el área.");
        }
    }

    private boolean esDniValido(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }

    private boolean esTelefonoCelularValido(String telefono) {
        return telefono != null && telefono.matches("9\\d{8}");
    }
}