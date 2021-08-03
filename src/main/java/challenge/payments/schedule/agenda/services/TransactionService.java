package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Client;
import challenge.payments.schedule.agenda.entities.details.ReceivablesDetails;
import challenge.payments.schedule.agenda.entities.details.TransactionDetails;
import challenge.payments.schedule.agenda.entities.Receivables;
import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.entities.builders.ReceivableDetailsBuilder;
import challenge.payments.schedule.agenda.entities.builders.TransactionDetailsBuilder;
import challenge.payments.schedule.agenda.exceptions.ApplicationException;
import challenge.payments.schedule.agenda.messages.Messages;
import challenge.payments.schedule.agenda.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public challenge.payments.schedule.agenda.entities.Transaction persist(TransactionDetails transactionDetails) {

        //Inserir tratativa de erro
        var client = this.clientService.findById(transactionDetails.getClientId()).orElseThrow(
                () -> new ApplicationException(
                        String.format(Messages.ENTITY_NOT_FOUND, clientService.getEntityName())
                )
        );
        var transaction = TransactionDetailsBuilder.toEntity(transactionDetails, client);


        return transactionRepository.save(transaction);

    }


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

        //inserir tratativa de erro aqui

       var fee = client.getPlano()
                .stream()
                .filter(plan -> plan.getInstallments()==transaction.getInstallment())
                .findFirst().get().getFees();

        var totalInstallments = Math.toIntExact(transaction.getInstallment());

        //inserir tratativa de erro aqui
        var modality = client.getPlano()
                .stream()
                .filter(plan -> plan.getInstallments()==installment)
                .findFirst().get().getModalities();

        Double amount = Math.round(transaction.getAmount()/transaction.getInstallment()*100d)/100.0;

        var expectedPaymentAt = transaction.getCreatedAt().toLocalDate();

        var totalNetAmount = (amount)*(1 - fee/100);

        var netAmount = Math.round((totalNetAmount)*100.0)/100.0;

        var receivable = Receivables.builder()
                .amount(amount)
                .installments((long) installment)
                .currentInstallments((long) installment)
                .expectedPaymentDate(expectedPaymentAt.plusDays((long) modality))
                .fee(fee)
                .netAmount(netAmount)
                .client(client)
                .transaction(transaction)
                .build();

        return receivableService.insert(receivable);

    }

}
