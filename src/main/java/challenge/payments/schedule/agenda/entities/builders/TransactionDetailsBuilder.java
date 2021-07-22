package challenge.payments.schedule.agenda.entities.builders;

import challenge.payments.schedule.agenda.entities.Client;
import challenge.payments.schedule.agenda.entities.Details.TransactionDetails;
import challenge.payments.schedule.agenda.entities.Transaction;

public class TransactionDetailsBuilder {

    public static Transaction toEntity(TransactionDetails details, Client client){
        return Transaction.builder()
                .amount(details.getAmount())
                .installment(details.getInstallments())
                .client(client)
                .build();
    }
}
