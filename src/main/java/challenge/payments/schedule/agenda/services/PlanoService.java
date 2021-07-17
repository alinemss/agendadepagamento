package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Plano;
import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.repositories.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private ClientService clientService;

    public Plano getPlan(Transaction transaction) {

        Integer idPlan = clientService.getClient(transaction).getIdt_plan();

        Plano plan = planoRepository.findAll()
                .stream()
                .filter(x -> x.getIdt_plan() == idPlan)
                .filter(x -> x.getTypeTransaction() == transaction.getTypeTransaction())
                .filter(x -> x.getInstallments() == transaction.getInstallments())
                .collect(Collectors.toList())
                .get(0);

        return plan;
    }

}
