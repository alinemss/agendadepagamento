package challenge.payments.schedule.agenda.repositories;

import challenge.payments.schedule.agenda.entities.Receivables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivableRepository extends JpaRepository<Receivables,Long> {
}
