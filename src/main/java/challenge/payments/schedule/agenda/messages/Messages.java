package challenge.payments.schedule.agenda.messages;

public class Messages {

        public static final String ENTITY_ALREADY_EXISTS = "101#Entity already exists: %s.";
        public static final String ENTITY_NOT_FOUND = "102#Entity not found: %s.";
        public static final String TYPE_MISMATCH_REQUEST = "103#Invalid %s: Type mismatch (%s).";
        public static final String REQUEST_METHOD_NOT_SUPPORTED = "104#Request method '%s' not supported or path parameter not found.";
        public static final String MALFORMED_JSON_REQUEST = "105#Malformed JSON request.";
        public static final String REQUEST_HEADER_PARAMETER_MISSING = "106#Request Header parameter is missing: %s.";
        public static final String DATABASE_ERROR = "107#Database Error: %s";
        public static final String DEFAULT_ERROR = "199#Internal error. If problem persists, contact support.";


        /**
         * Business Messages
         */

        public static final String LOGGER_ERROR = "201#Logger Error.";
        public static final String ENTITY_UUID_SHOULD_BE_NOT_NULL = "202#Entity UUID should not be null.";
        public static final String ENTITY_CANNOT_BE_DELETED_BECAUSE_IT_DOESNT_EXIST_OR_IT_WAS_ALREADY_DELETE = "203#Entity cannot be deleted because it doesn't exist or it was already deleted.";
        public static final String FIELD_SHOULD_NOT_BE_NULL = "204#Field %s should not be null.";
        public static final String FIELD_SHOULD_BE_POSITIVE = "205#Field %s should be positive.";
        public static final String FIELD_MIN_VALUE_CONSTRAINTS = "206#Field %s min value constraints: 1";
        public static final String FIELD_MAX_VALUE_CONSTRAINTS = "207#Field %s max value constraints: 12";
        /**
         * Integration Messages
         */

        public static final String PROVIDER_NOT_FOUND = "501#Provider not found: %s.";
        public static final String PROVIDER_INTEGRATION_ERROR = "502#Provider integration error: %s";


        public static Integer getCode(String message) {
            return Integer.valueOf(message.substring(0, 3));
        }
}
