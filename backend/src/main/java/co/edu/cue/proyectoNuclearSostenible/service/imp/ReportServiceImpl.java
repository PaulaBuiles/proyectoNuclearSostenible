package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.ReportDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ReportDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.ReportMapper;
import co.edu.cue.proyectoNuclearSostenible.service.ReportService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReportMapper reportMapper;

    /**
     * Crea un nuevo reporte y actualiza las relaciones de usuarios involucrados.
     *
     * @param reportDto El reporte a crear.
     * @return El reporte creado.
     */
    public ReportDto createReport(ReportDto reportDto) {
        Report report = reportMapper.mapToEntity(reportDto);

        report.setComplainant(userDao.findById(reportDto.complainantId()).get());
        report.setDenounced(userDao.findById(reportDto.denouncedId()).get());
        Report savedReport = reportDao.save(report);

        User denounced = report.getDenounced();
        denounced.getOwnComplaints().add(savedReport);
        userDao.save(denounced);

        User complainant = savedReport.getComplainant();
        complainant.getComplaintsMade().add(savedReport);
        userDao.save(complainant);

        checkAndBlockUser(denounced);

        return reportMapper.mapToDTO(savedReport);
    }

    /**
     * Verifica el número de quejas de un usuario y lo bloquea si es necesario.
     *
     * @param user El usuario a verificar.
     */
    private void checkAndBlockUser(User user) {
        if (user.getOwnComplaints().size() >= 7) {
            user.setStatus(false);
            userDao.save(user);
        }
    }

    /**
     * Encuentra un reporte por su ID.
     *
     * @param id El ID del reporte a buscar.
     * @return Un Optional que contiene el reporte si se encuentra.
     */
    public Optional<Report> findReportById(Long id) {
        return reportDao.findById(id);
    }

    /**
     * Encuentra todos los reportes.
     *
     * @return Una lista de todos los reportes.
     */
    public List<Report> findAllReports() {
        return reportDao.findAll();
    }

    /**
     * Actualiza un reporte existente.
     *
     * @param report El reporte a actualizar.
     * @return El reporte actualizado.
     */
    public Report updateReport(Report report) {
        return reportDao.save(report);
    }

    public List<Report> getReportByUserId(Long id) {
        return reportDao.findByComplainant_IdUserOrDenounced_IdUser(id, id);
    }


}
