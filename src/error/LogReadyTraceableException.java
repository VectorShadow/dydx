package error;

public class LogReadyTraceableException extends Exception {
    public LogReadyTraceableException() {
        super("Unspecified exception occurred.");
    }
    public LogReadyTraceableException(String errorMessage) {
        super(errorMessage);
    }
}
