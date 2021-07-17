package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Client;
import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public Optional<Client> findByUserId(Long id) {

        Optional<Client> dataClient = clientRepository.findById(id);

        return dataClient;

    }

    //Encontra o IdtPlan Transação
    public Client getClient(Transaction transaction) {

        Long userId = transaction.getUserId();
        Client client = clientRepository.findAll()
                .stream()
                .filter(x -> x.getUserId() == userId)
                .collect(Collectors.toList())
                .get(0);

        return client;
    }




}
