package challenge.payments.schedule.agenda.entities;


import challenge.payments.schedule.agenda.messages.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "transaction")
public class Transaction {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  @NotNull(message = Messages.FIELD_SHOULD_NOT_BE_NULL)
  private Client client;

  @NotNull(message = Messages.FIELD_SHOULD_NOT_BE_NULL)
  @Positive(message = Messages.FIELD_SHOULD_BE_POSITIVE)
  private Double amount;

  @NotNull(message = Messages.FIELD_SHOULD_NOT_BE_NULL)
  @Positive(message = Messages.FIELD_SHOULD_BE_POSITIVE)
  @Min(value = 0, message = Messages.FIELD_MIN_VALUE_CONSTRAINTS)
  @Max(value = 12, message = Messages.FIELD_MAX_VALUE_CONSTRAINTS)
  private Long installment;

  @Column(updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

}
