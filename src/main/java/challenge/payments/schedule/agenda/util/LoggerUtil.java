package challenge.payments.schedule.agenda.util;
import challenge.payments.schedule.agenda.exceptions.ApplicationException;
import challenge.payments.schedule.agenda.messages.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LoggerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtil.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void info(Object entity) {
        try {
            String value = OBJECT_MAPPER.writeValueAsString(entity);
            LOGGER.info(value);
        } catch (Exception exception) {
            throw new ApplicationException(Messages.LOGGER_ERROR);
        }
    }

    public static void error(Object entity) {
        try {
            String value = OBJECT_MAPPER.writeValueAsString(entity);
            LOGGER.error(value);
        } catch (Exception exception) {
            throw new ApplicationException(Messages.LOGGER_ERROR);
        }
    }

    public static void info (HttpTrace trace) {
        Marker marker = MarkerFactory.getMarker("http-trace");
        try {
            String value = OBJECT_MAPPER.writeValueAsString(trace);
            LOGGER.info(marker, value);
        } catch (Exception exception) {
            throw new ApplicationException(Messages.LOGGER_ERROR);
        }
    }

}
