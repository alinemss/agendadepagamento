package challenge.payments.schedule.agenda.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
  private Client client;

  private Double amount;

  private Long installment;

  @Column(updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

}
