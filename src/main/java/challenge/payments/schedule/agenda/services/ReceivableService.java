package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Receivables;
import challenge.payments.schedule.agenda.repositories.ReceivableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceivableService {

    @Autowired
    private ReceivableRepository receivableRepository;

    @Autowired
    private ClientService clientService;

    //Construir Um find by id para os receivables

    public List<Receivables> receivablesList(Receivables receivables) {

        return receivableRepository.findAll()
                .stream()
                .filter(x -> x.getIdReceivable() == receivables.getIdReceivable())
                .collect(Collectors.toList());

    }

}
