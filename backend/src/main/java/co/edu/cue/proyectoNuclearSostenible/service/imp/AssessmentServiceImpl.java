package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.service.AssesmentService;

@Service
@AllArgsConstructor
public class AssessmentServiceImpl implements AssesmentService {

    @Autowired
    private AssessmentDao assessmentDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AssessmentMapper assessmentMapper;

}
