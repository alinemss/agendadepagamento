package challenge.payments.schedule.agenda.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "receivable")
public class Receivables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long idReceivable;

    @OneToOne
    private Client client;

    @OneToOne
    private Transaction transaction;

    @Column
    private Double fee;

    @Column
    private Long currentInstallments;

    @Column
    private Double amount;

    @Column
    private Double netAmount;

    @Column
    private Long installments;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column()
    private LocalDate expectedPaymentDate;

}
