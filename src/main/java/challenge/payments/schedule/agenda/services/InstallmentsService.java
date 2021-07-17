package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Installment;
import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.repositories.InstallmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstallmentsService {

    @Autowired
    private InstallmentsRepository installmentsRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PlanoService planoService;


    public List<Installment> installmentList(Transaction transaction) {

        List<Installment> installments = installmentsRepository.findAll();

        return installments
                .stream()
                .filter(x -> x.getIdTransaction() == transaction.getIdTransaction())
                .collect(Collectors.toList());

    }


}
