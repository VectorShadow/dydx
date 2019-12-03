package linker.local;

import data.AbstractDatum;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.ClientDataLink;

public class ClientLocalDataLink extends AbstractLocalDataLink implements ClientDataLink {
    @Override
    public void handle(byte instruction, AbstractDatum datum) {
        switch (instruction){
            //todo - more cases
            default:
                ErrorLogger.logFatalException(new LogReadyTraceableException("Improper instruction."));
        }
    }
}
