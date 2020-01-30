package data;

import error.ErrorLogger;

import java.io.*;

public class StreamConverter {
    public static byte[] toByteArray(AbstractDatum ad) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] b = new byte[]{};
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(ad);
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
    public static AbstractDatum toObject(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInput in = null;
        AbstractDatum ad = null;
        try {
            in = new ObjectInputStream(bis);
            ad = (AbstractDatum)in.readObject();
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
        return ad;
    }
}
