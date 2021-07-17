package challenge.payments.schedule.agenda.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "Planos")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idt_plan;

    @Column
    @JoinColumn(table = "Transactions", columnDefinition = "typeTransaction")
    private String typeTransaction;

    @Column
    private float fee;

    @Column
    @JoinColumn(table = "Transactions", columnDefinition = "installments")
    private int installments;

    @Column
    private int daysUntilPayment;

}