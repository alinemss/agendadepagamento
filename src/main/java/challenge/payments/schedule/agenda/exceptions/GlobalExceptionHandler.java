package challenge.payments.schedule.agenda.exceptions;

import challenge.payments.schedule.agenda.messages.ApplicationMessage;
import challenge.payments.schedule.agenda.messages.Messages;
import challenge.payments.schedule.agenda.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@ResponseStatus
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleConstraintViolationException( ConstraintViolationException exception) {
        List<ApplicationMessage> messages = exception.getConstraintViolations()
                .stream()
                .map(constraint -> ApplicationMessage.parse(constraint.getMessage()))
                .collect(Collectors.toList());
        return errors(messages);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var messages = ex.getBindingResult().getFieldErrors().stream()
                .map( error -> ApplicationMessage.parse(
                        String.format(Objects.requireNonNull(error.getDefaultMessage()), error.getField())
                ))
                .collect(Collectors.toList());
        return errors(messages);
    }


    //Path parameter type validation

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return errors(
                Collections.singletonList(
                        ApplicationMessage.parse(
                                String.format(Messages.TYPE_MISMATCH_REQUEST, ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName())
                        )
                )
        );
    }

 //Path parameter not found

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return errors(
                Collections.singletonList(
                        ApplicationMessage.parse(
                                String.format(Messages.REQUEST_METHOD_NOT_SUPPORTED, ex.getMethod())
                        )
                )
        );
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return errors(ApplicationMessage.parse(Messages.MALFORMED_JSON_REQUEST));
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<Map<String, List<ApplicationMessage>>> handleApplicationException(ApplicationException ex) {
        var errors = errors(ApplicationMessage.parse(ex.getMessage()));
        return ResponseEntity.status(ex.getStatus()).body(errors);
    }

    @ExceptionHandler(ApplicationInternalException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, List<ApplicationMessage>> handleApplicationException(ApplicationInternalException ex) {
        var serializedError = ApplicationMessage.parse(Messages.DEFAULT_ERROR);
        errors(ApplicationMessage.parse(ex.getMessage()));
        return errors(serializedError, false);
    }

    @ResponseBody
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return errors(ApplicationMessage.parse(String.format(Messages.REQUEST_HEADER_PARAMETER_MISSING, ex.getParameter().getParameter().getName())));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleWebExchangeBindException(WebExchangeBindException ex) {
        return errors(
                ex.getAllErrors().stream().map(error -> ApplicationMessage.parse(Objects.requireNonNull(error.getDefaultMessage()))).collect(Collectors.toList())
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, List<ApplicationMessage>> handleNotMappedException(Exception exception) {
        return errors(ApplicationMessage.parse(Messages.DEFAULT_ERROR), exception.getMessage());
    }

    private Map<String, List<ApplicationMessage>> errors(List<ApplicationMessage> messages) {
        return errors(messages, true);
    }
    private Map<String, List<ApplicationMessage>> errors(List<ApplicationMessage> messages, Boolean logMessages) {
        if (logMessages) LoggerUtil.error(messages);
        return Collections.singletonMap("errors", messages);
    }

    private Map<String, List<ApplicationMessage>> errors(ApplicationMessage message) {
        return errors(message, true);
    }

    private Map<String, List<ApplicationMessage>> errors(ApplicationMessage message, Boolean logMessages) {
        if (logMessages) LoggerUtil.error(message);
        return Collections.singletonMap("errors", Collections.singletonList(message));
    }

    private Map<String, List<ApplicationMessage>> errors(ApplicationMessage message, String internalMessage, Boolean logMessages) {
        if (logMessages) LoggerUtil.error(String.format("%s: %s", message, internalMessage));
        return Collections.singletonMap("errors", Collections.singletonList(message));
    }

    private Map<String, List<ApplicationMessage>> errors(ApplicationMessage message, String internalMessage) {
        return errors(message, internalMessage, true);
    }
}
