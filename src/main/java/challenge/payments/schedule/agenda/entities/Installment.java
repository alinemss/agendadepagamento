package challenge.payments.schedule.agenda.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Installment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long idParcela;

    @Column()
    private Long idTransaction;

    //Foreight kEY
    @Column()
    private Long userId;

    //Foreight kEY
    @Column
    private String userName;

    @Column
    private String typeTransaction;

    @Column
    private float fee;

    @Column
    private int currentInstallments;

    @Column
    private float installmentValue;

    @Column
    private float amountSplit;

    @Column
    private Date expectedPaymentDate;

    @Column
    private int installments;


}
