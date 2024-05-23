package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.ReportDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
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

    public Report createReport(Report report) {
        Report savedReport = reportDao.save(report);
        User denounced = savedReport.getDenounced();
        denounced.getOwnComplaints().add(savedReport);
        userDao.save(denounced);

        User complainant = savedReport.getComplainant();
        complainant.getComplaintsMade().add(savedReport);
        userDao.save(complainant);

        checkAndBlockUser(denounced);

        return savedReport;
    }

    private void checkAndBlockUser(User user) {
        if (user.getOwnComplaints().size() >= 7) {
            user.setStatus(false);
            userDao.save(user);
        }
    }

    public Optional<Report> findReportById(Long id) {
        return reportDao.findById(id);
    }

    public List<Report> findAllReports() {
        return reportDao.findAll();
    }

    public Report updateReport(Report report) {
        return reportDao.save(report);
    }

}
