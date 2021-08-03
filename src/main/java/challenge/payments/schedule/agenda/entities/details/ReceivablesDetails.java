package challenge.payments.schedule.agenda.entities.details;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReceivablesDetails {

    private String clientName;
    private Double fee;
    private Long currentInstallment;
    private Long installments;
    private Double amount;
    private Double netAmount;
    private LocalDate expectedPaymentDate;

}
