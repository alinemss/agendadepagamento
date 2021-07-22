package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Client;
import challenge.payments.schedule.agenda.entities.Details.ReceivablesDetails;
import challenge.payments.schedule.agenda.entities.Details.TransactionDetails;
import challenge.payments.schedule.agenda.entities.Plano;
import challenge.payments.schedule.agenda.entities.Receivables;
import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.entities.builders.ReceivableDetailsBuilder;
import challenge.payments.schedule.agenda.entities.builders.TransactionDetailsBuilder;
import challenge.payments.schedule.agenda.repositories.ReceivableRepository;
import challenge.payments.schedule.agenda.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.round;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ReceivableRepository receivableRepository;

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
        var plan = client.getPlano();
        var totalInstalmments = transaction.getInstallment();
        var startAt = totalInstalmments == 0 ? 0 : 1;
        var endAt = Math.toIntExact(transactionDetails.getInstallments()) + 1;

        return IntStream.range(startAt,endAt)
                .mapToObj(installment -> this.installmentToSchedule(installment,transaction,plan,client))
                .map(ReceivableDetailsBuilder::fromEntity)
                .collect(Collectors.toList());



    }

    //Gera os Recibos e Agenda --> Builda os parcelamentos
    public Receivables installmentToSchedule(int installment, Transaction transaction, Plano plano, Client client) {

        var totalInstallments = Math.toIntExact(transaction.getInstallment());
        var fee = plano.getFees().doubleValue();
        var modality = 30;
        var amount = transaction.getAmount()/transaction.getInstallment();
        //Corrigir regra de dias add

        var expectedPaymentAt = transaction.getCreatedAt().toLocalDate();

        var totalNetAmount = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(1 - fee/100));

        var netAmount = round(totalNetAmount.divide(BigDecimal.valueOf(totalInstallments)).doubleValue() * 100.0)/100.0;

        return Receivables.builder()
                .amount(amount)
                .installments((long) installment)
                .expectedPaymentDate(expectedPaymentAt.plusDays((long) modality * installment))
                .fee(fee)
                .netAmount(netAmount)
                .client(client)
                .transaction(transaction)
                .build();

    }

}
