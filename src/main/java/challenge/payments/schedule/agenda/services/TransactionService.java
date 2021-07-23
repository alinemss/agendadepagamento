package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Client;
import challenge.payments.schedule.agenda.entities.Details.ReceivablesDetails;
import challenge.payments.schedule.agenda.entities.Details.TransactionDetails;
import challenge.payments.schedule.agenda.entities.Receivables;
import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.entities.builders.ReceivableDetailsBuilder;
import challenge.payments.schedule.agenda.entities.builders.TransactionDetailsBuilder;
import challenge.payments.schedule.agenda.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ReceivableService receivableService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PlanService planService;

    //Não esta em uso
    private Optional<challenge.payments.schedule.agenda.entities.Transaction> findTransactionById(Long id){
        return transactionRepository.findById(id);
    }

    //Funcionando \\o//
    public challenge.payments.schedule.agenda.entities.Transaction persist(TransactionDetails transactionDetails){

        var client = clientService.findById(transactionDetails.getClientId()).stream().collect(Collectors.toList()).get(0);
        var transaction = TransactionDetailsBuilder.toEntity(transactionDetails,client);

        return transactionRepository.save(transaction);
    }
    //Chama a transação e retorna a agenda
    public List<ReceivablesDetails> handleReceivable(TransactionDetails transactionDetails) {
        var transaction = this.persist(transactionDetails);
        var client = transaction.getClient();
        var totalInstalmments = transaction.getInstallment();
        var startAt = totalInstalmments == 0 ? 0 : 1;
        var endAt = Math.toIntExact(transactionDetails.getInstallments()) + 1;

        return IntStream.range(startAt,endAt)
                .mapToObj(installment -> this.installmentToSchedule(installment,transaction,client))
                .map(ReceivableDetailsBuilder::fromEntity)
                .collect(Collectors.toList());
    }

    public Receivables installmentToSchedule(int installment, Transaction transaction, Client client) {

        var fee = client.getPlano()
                .stream()
                .filter(plan -> plan.getInstallments()==transaction.getInstallment())
                .findFirst().get().getFees();
        ;
        var totalInstallments = Math.toIntExact(transaction.getInstallment());

        var modality = client.getPlano()
                .stream()
                .filter(plan -> plan.getInstallments()==installment)
                .findFirst().get().getModalities();

        var amount = transaction.getAmount()/transaction.getInstallment();
        //Corrigir regra de dias add

        var expectedPaymentAt = transaction.getCreatedAt().toLocalDate();

        var totalNetAmount = (amount)*(1 - fee/100);

        var netAmount = Math.round((totalNetAmount)*100.0)/100.0;

        var receivable = Receivables.builder()
                .amount(amount)
                .installments((long) installment)
                .expectedPaymentDate(expectedPaymentAt.plusDays((long) modality))
                .fee(fee)
                .netAmount(netAmount)
                .client(client)
                .transaction(transaction)
                .build();

        return receivableService.insert(receivable);

    }

}
