package data;

import error.ErrorLogger;

import java.io.*;

public class StreamConverter {
    public static byte[] toByteArray(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] b = new byte[]{};
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();
            b = bos.toByteArray();
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return b;
    }
    public static Object toObject(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return o;
    }
}
