package challenge.payments.schedule.agenda.services;

import challenge.payments.schedule.agenda.entities.Client;
import challenge.payments.schedule.agenda.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Optional<Client> findById(Long id) {

        return clientRepository.findById(id);

    }

}
