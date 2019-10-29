package error;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorLogger {
    private static final String LOGPATH = "DebugReport";

    /**
     * Final step in handling Exceptions. Goes in the catch block of the try catch encompassing
     * all thread starting code.
     */
    public static void logFatalException(LogReadyTraceableException lrte) {
        final String timestampedDebugReport = LOGPATH + System.currentTimeMillis() + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(timestampedDebugReport);
            fileWriter.write(lrte.getMessage());
            fileWriter.close();
        } catch (IOException e) {
            System.exit(-2);
        }
        System.exit(-1);
    }

    /**
     * Convert an exception into a LogReadyTraceableException in preparation for being thrown to a
     * thread encompassing try-catch block.
     */
    public static LogReadyTraceableException trace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new LogReadyTraceableException(sw.toString());
    }
}
