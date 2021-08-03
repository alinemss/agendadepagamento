package challenge.payments.schedule.agenda.controllers;

import challenge.payments.schedule.agenda.entities.details.ReceivablesDetails;
import challenge.payments.schedule.agenda.entities.details.TransactionDetails;
import challenge.payments.schedule.agenda.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    private String string(){
        return "Hello World";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ReceivablesDetails> postInstalments (@Valid @RequestBody TransactionDetails Transaction){

       return transactionService.handleReceivable(Transaction);

}

}
