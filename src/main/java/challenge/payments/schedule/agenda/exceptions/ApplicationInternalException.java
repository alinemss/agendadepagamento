package challenge.payments.schedule.agenda.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ApplicationInternalException extends RuntimeException {

    private HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

    public ApplicationInternalException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ApplicationInternalException(String message, String logMessage) {
        super(message, new Error(logMessage));
    }

    public ApplicationInternalException(String message) {
        super(message);
    }

    public ApplicationInternalException(String message, HttpStatus status) {
        super(message);
        this.setStatus(status);
    }
}
