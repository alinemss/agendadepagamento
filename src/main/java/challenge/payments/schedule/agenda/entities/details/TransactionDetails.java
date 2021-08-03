package challenge.payments.schedule.agenda.entities.details;

import challenge.payments.schedule.agenda.messages.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {

    @NotNull(message = Messages.FIELD_SHOULD_NOT_BE_NULL)
    @Positive(message = Messages.FIELD_SHOULD_BE_POSITIVE)
    private Long clientId;

    @NotNull(message = Messages.FIELD_SHOULD_NOT_BE_NULL)
    @Positive(message = Messages.FIELD_SHOULD_BE_POSITIVE)
    private Double amount;

    @NotNull(message = Messages.FIELD_SHOULD_NOT_BE_NULL)
    @Positive(message = Messages.FIELD_SHOULD_BE_POSITIVE)
    @Min(value = 0, message = Messages.FIELD_MIN_VALUE_CONSTRAINTS)
    @Max(value = 12, message = Messages.FIELD_MAX_VALUE_CONSTRAINTS)
    private Long installments;

}


