package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IAulaBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AulaDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.GradoSeccionDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.PeriodoAcademicoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.AulaDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.GradoSeccionDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.PeriodoAcademicoDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaCabeceraDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.AulaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

import java.util.List;

public class AulaBL implements IAulaBL {

    private AulaDAO aulaDAO = new AulaDAOImpl();
    private MatriculaCabeceraDAO matriculaCabeceraDAO = new MatriculaCabeceraDAOImpl();
    private PeriodoAcademicoDAOImpl periodoDAO = new PeriodoAcademicoDAOImpl(); // o su interfaz
    private GradoSeccionDAOImpl gradoSeccionDAO = new GradoSeccionDAOImpl(); // o su interfaz

    @Override
    public List<AulaDTO> listarAulas(String periodo, String nivel, String grado, String estado, String codigo) throws Exception {
        try {
            TransactionContext.getConnection();
            List<AulaDTO> lista = aulaDAO.listarAulas(periodo, nivel, grado, estado, codigo);
            TransactionContext.commit();
            return lista;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar aulas: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public AulaDTO obtenerDetalleAula(int idAula) throws Exception {
        try {
            TransactionContext.getConnection();
            AulaDTO aula = aulaDAO.obtenerDetalleAula(idAula);
            TransactionContext.commit();
            return aula;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al obtener detalle del aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Aula guardarAula(Aula aula) throws Exception {
        try {
            TransactionContext.getConnection();

            if (aulaDAO.existeCodigoAula(aula.getCodigo(), 0)) {
                throw new Exception("Ya existe un aula con ese código.");
            }

            Aula registrada = aulaDAO.save(aula);
            TransactionContext.commit();
            return registrada;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al guardar aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Aula actualizarAula(Aula aula) throws Exception {
        try {
            TransactionContext.getConnection();

            if (aulaDAO.existeCodigoAula(aula.getCodigo(), aula.getIdAula())) {
                throw new Exception("Ya existe un aula con ese código.");
            }

            Aula actualizada = aulaDAO.update(aula);
            TransactionContext.commit();
            return actualizada;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al actualizar aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public void eliminarAula(int idAula) throws Exception {
        try {
            TransactionContext.getConnection();

            Aula aula = new Aula();
            aula.setIdAula(idAula);

            aulaDAO.remove(aula);
            TransactionContext.commit();
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al eliminar aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public boolean actualizarCapacidad(int idAula, int capacidad) throws Exception {
        try {
            TransactionContext.getConnection();
            boolean ok = aulaDAO.actualizarCapacidad(idAula, capacidad);
            TransactionContext.commit();
            return ok;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al actualizar capacidad: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
    @Override
    public AulaDTO guardarAulaPorNivel(AulaDTO aulaDTO) throws Exception {
        try {
            // 1. Iniciar la transacción única para ambas tablas
            TransactionContext.getConnection();

            // 2. Validar duplicados de infraestructura física
            if (aulaDAO.existeCodigoAula(aulaDTO.getCodigo(), 0)) {
                throw new Exception("Ya existe un aula con el código: " + aulaDTO.getCodigo());
            }

            // 3. Crear e insertar el aula física
            Aula aulaFisica = new Aula();
            aulaFisica.setCodigo(aulaDTO.getCodigo());
            aulaFisica.setTipo(pe.edu.pucp.SIME.model.gestionAcademica.TipoAula.valueOf(aulaDTO.getTipoAula().toUpperCase()));
            aulaFisica.setCapacidad(aulaDTO.getCapacidad());
            aulaFisica.setActivo(true);

            // Se guarda físicamente; recuerda que tu DAO de aula debe retornar el objeto con el ID autogenerado
            Aula aulaRegistrada = aulaDAO.save(aulaFisica);

            // 4. Resolver los IDs correspondientes utilizando los nuevos métodos de consulta
            int idPeriodo = periodoDAO.obtenerIdPorAnio(Integer.parseInt(aulaDTO.getPeriodo()));
            int idGradoSeccion = gradoSeccionDAO.obtenerIdPorNivelYGrado(aulaDTO.getNivel(), aulaDTO.getGrado());

            // 5. Construir la cabecera de matrícula respetando la estructura que pide tu MatriculaCabeceraDAOImpl
            MatriculaCabecera cabecera = new MatriculaCabecera();

            // Seteamos el objeto PeriodoAcademico solo con su ID
            PeriodoAcademico pa = new PeriodoAcademico();
            pa.setIdPeriodoAcademico(idPeriodo);
            cabecera.setPeriodoAcademico(pa);

            // Seteamos el objeto GradoSeccion solo con su ID
            GradoSeccion gs = new GradoSeccion();
            gs.setIdGradoSeccion(idGradoSeccion);
            cabecera.setGradoSeccion(gs);

            // Seteamos el objeto Aula que acabamos de registrar y que ya contiene su idAula asignado
            cabecera.setAula(aulaRegistrada);

            // Seteamos el resto de los campos requeridos por tu PreparedStatement
            cabecera.setTotalVacantes(aulaDTO.getCapacidad());
            cabecera.setVacantesOcupadas(0);
            cabecera.setFechaInicioMatricula(new java.util.Date()); // Fecha de hoy

            // Fecha fin estimada (puedes parametrizarla o calcularla dinámicamente)
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(Integer.parseInt(aulaDTO.getPeriodo()), java.util.Calendar.MARCH, 15);
            cabecera.setFechaFinMatricula(cal.getTime());

            cabecera.setActivo(true);

            // 6. Guardar en la tabla asociativa SIME_MATRICULA_CABECERA
            matriculaCabeceraDAO.save(cabecera);

            // Si todo anduvo bien, impactamos los cambios juntos en la Base de Datos
            TransactionContext.commit();

            // Retornamos el DTO con el ID del aula física para el front-end
            aulaDTO.setIdAula(aulaRegistrada.getIdAula());
            return aulaDTO;

        } catch (Exception e) {
            // Si falla la inserción del aula física OR la de la cabecera, hacemos Rollback completo
            TransactionContext.rollback();
            throw new Exception("Error al registrar el aula asignada al nivel: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
}