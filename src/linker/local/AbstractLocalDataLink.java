package linker.local;

import data.StreamConverter;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;

/**
 * defines the behavior of a DataLink between client and server connected locally
 * instead of streaming through a socket, we can directly transfer instructions
 */
public abstract class AbstractLocalDataLink extends AbstractDataLink {

    private class ByteArrayPointer {
        byte[] value = null;

        public void set(byte[] val) {
            value = val;
        }
        public byte[] get() {
            return value;
        }
    }

    ByteArrayPointer input = new ByteArrayPointer();
    ByteArrayPointer output = new ByteArrayPointer();

    public static ClientLocalDataLink generateClientLink(ServerLocalDataLink lsdl) {
        ClientLocalDataLink lcdl = new ClientLocalDataLink();
        lcdl.input = lsdl.output;
        lcdl.output = lsdl.input;
        lsdl.start();
        lcdl.start();
        return lcdl;
    }

    @Override
    public void send(byte[] transmission) {
        while(output.get() != null) { //don't overwrite the current output until it's been handled
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                ErrorLogger.logFatalException(ErrorLogger.trace(e));
            }
        }
        output.set(transmission);
    }

    @Override
    public void listen() throws LogReadyTraceableException {
        for (;;) {
            while (input.get() == null) { //wait until we have something to do
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw ErrorLogger.trace(e);
                }
            }
            handle(input.get());
            input.set(null);
        }
    }
    private void handle(byte[] bytes) {
        byte instruction = bytes[0];
        byte[] body = new byte[bytes.length - 4];
        for (int i = 0; i < body.length; ++i) {
            body[i] = bytes[i + 4];
        }
        try {
            handle(instruction, StreamConverter.toObject(body));
        }catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
    @Override
    public void run() {
        try {
            listen();
        } catch (LogReadyTraceableException e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
