package challenge.payments.schedule.agenda.repositories;

import challenge.payments.schedule.agenda.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository  extends JpaRepository<Client,Long> {
}
