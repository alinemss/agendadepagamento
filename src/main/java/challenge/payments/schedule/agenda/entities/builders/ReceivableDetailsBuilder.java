package challenge.payments.schedule.agenda.entities.builders;

import challenge.payments.schedule.agenda.entities.Client;
import challenge.payments.schedule.agenda.entities.Details.ReceivablesDetails;
import challenge.payments.schedule.agenda.entities.Receivables;
import challenge.payments.schedule.agenda.entities.Transaction;

import java.util.List;

public class ReceivableDetailsBuilder {

    public static ReceivablesDetails fromEntity(Receivables entity) {
        var client = entity.getClient();
        var transaction = entity.getTransaction();
        return ReceivablesDetails.builder()
                .clientName(client.getUserName())
                .netAmount(entity.getNetAmount())
                .fee(entity.getFee())
                .expectedPaymentDate(entity.getExpectedPaymentDate())
                .currentInstallment(entity.getInstallments())
                .installments(transaction.getInstallment())
                .amount(entity.getAmount())
                .build();
    }

}