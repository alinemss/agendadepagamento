package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private InstallmentsService installmentsService;

    public void createTransaction(Transaction transaction) {

        transactionRepository.save(transaction);

        //installmentsService.createInstallments(transaction);


    }


}
