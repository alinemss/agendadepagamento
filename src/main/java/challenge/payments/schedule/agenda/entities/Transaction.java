package challenge.payments.schedule.agenda.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;

    @JoinColumn(table = "Clients", referencedColumnName = "userId")
    @Column()
    private Long userId;

    @Column()
    private float amount;

    @Column()
    private int installments;

    @Column()
    private String typeTransaction;

    @Column()
    private LocalDate transactionDate = LocalDate.now();

}


