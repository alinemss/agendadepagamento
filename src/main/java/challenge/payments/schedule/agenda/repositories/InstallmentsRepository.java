package challenge.payments.schedule.agenda.repositories;

import challenge.payments.schedule.agenda.entities.Installment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentsRepository extends JpaRepository<Installment,Long> {
}
