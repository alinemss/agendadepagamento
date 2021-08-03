package challenge.payments.schedule.agenda.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ApplicationException extends RuntimeException{

    private HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

    public ApplicationException(String message){
        super(message);
    }

    public ApplicationException(Throwable throwable){
        super(throwable.getMessage(),throwable.getCause());
    }

    public ApplicationException(String message, Throwable throwable){
        super(message,throwable);
    }

    public ApplicationException(String message, String logMessage){
        super(message,new Error(logMessage));
    }

    public ApplicationException(String message, HttpStatus status){
        super(message);
        this.setStatus(status);

    }

}
