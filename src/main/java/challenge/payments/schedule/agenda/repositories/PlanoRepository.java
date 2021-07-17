package challenge.payments.schedule.agenda.repositories;

import challenge.payments.schedule.agenda.entities.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoRepository extends JpaRepository<Plano,Long> {
}
