package challenge.payments.schedule.agenda.repositories;

import challenge.payments.schedule.agenda.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
