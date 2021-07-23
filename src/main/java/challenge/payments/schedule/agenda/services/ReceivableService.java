package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Receivables;
import challenge.payments.schedule.agenda.repositories.ReceivableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivableService {

    @Autowired
    private ReceivableRepository receivableRepository;

    public Receivables insert(Receivables receivable) {
        return receivableRepository.save(receivable);
    }
}
