package challenge.payments.schedule.agenda.controllers;

import challenge.payments.schedule.agenda.entities.Installment;
import challenge.payments.schedule.agenda.entities.Transaction;
import challenge.payments.schedule.agenda.services.InstallmentsService;
import challenge.payments.schedule.agenda.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private InstallmentsService installmentsService;

    @GetMapping("/")
    private String string(){
        return "Hello World";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Installment> postInstalments (@RequestBody Transaction transaction){

       transactionService.createTransaction(transaction);

       return installmentsService.installmentList(transaction);

}

}
