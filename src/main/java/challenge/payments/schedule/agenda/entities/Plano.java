package challenge.payments.schedule.agenda.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "plans")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_plan")
    private Long id;

    private Long installments;

    private Double fees;

    private Integer modalities;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


}