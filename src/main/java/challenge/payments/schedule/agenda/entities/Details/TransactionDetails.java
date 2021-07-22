package challenge.payments.schedule.agenda.entities.Details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransactionDetails {

    private Long clientId;

    private Double amount;

    private Long installments;

}


